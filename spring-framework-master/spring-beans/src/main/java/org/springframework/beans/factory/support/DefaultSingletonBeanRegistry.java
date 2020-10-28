/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory.support;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.core.SimpleAliasRegistry;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Generic registry for shared bean instances, implementing the {@link
 * org.springframework.beans.factory.config.SingletonBeanRegistry}. Allows for registering singleton
 * instances that should be shared for all callers of the registry, to be obtained via bean name.
 *
 * <p>Also supports registration of
 * {@link org.springframework.beans.factory.DisposableBean} instances, (which might or might not
 * correspond to registered singletons), to be destroyed on shutdown of the registry. Dependencies
 * between beans can be registered to enforce an appropriate shutdown order.
 *
 * <p>This class mainly serves as base class for
 * {@link org.springframework.beans.factory.BeanFactory} implementations, factoring out the common
 * management of singleton bean instances. Note that the {@link org.springframework.beans.factory.config.ConfigurableBeanFactory}
 * interface extends the {@link SingletonBeanRegistry} interface.
 *
 * <p>Note that this class assumes neither a bean definition concept
 * nor a specific creation process for bean instances, in contrast to {@link AbstractBeanFactory}
 * and {@link DefaultListableBeanFactory} (which inherit from it). Can alternatively also be used as
 * a nested helper to delegate to.
 *
 * @author Juergen Hoeller
 * @see #registerSingleton
 * @see #registerDisposableBean
 * @see org.springframework.beans.factory.DisposableBean
 * @see org.springframework.beans.factory.config.ConfigurableBeanFactory
 * SingletionBean注册器的默认实现，同时继承SimpleAliasRegistry。 因此这个类可以有别名注册的功能和单例bean注册的功能，并且他还支持注册DisposableBean实例;
 * 它依赖ObjectFactory接口和DisposableBean接口(关闭注册表时调用到了destroy方法)。 侧重于Bean的注册,销毁,以及依赖关系(关联关系)的注册和销毁
 * @since 2.0
 */
public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements
		SingletonBeanRegistry {

	/**
	 * Maximum number of suppressed exceptions to preserve.
	 */
	private static final int SUPPRESSED_EXCEPTIONS_LIMIT = 100;

	// 是存放singleton对象的缓存
	/**
	 * Cache of singleton objects: bean name to bean instance.
	 */
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
	// 是存放制造singleton的工厂对象的缓存
	/**
	 * Cache of singleton factories: bean name to ObjectFactory.
	 */
	private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);
	// 是存放singletonFactory 制造出来的 singleton 的缓存早期单例对象缓存</span><span>
	/**
	 * Cache of early singleton objects: bean name to bean instance.
	 */
	private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);
	// 以上三个缓存是这个类存放单例bean的主要Map
	//单例注册表
	/**
	 * Set of registered singletons, containing the bean names in registration order.
	 */
	private final Set<String> registeredSingletons = new LinkedHashSet<>(256);

	/**
	 * Names of beans that are currently in creation. 目前正在创建中的单例bean的名称的集合
	 */
	private final Set<String> singletonsCurrentlyInCreation =
			Collections.newSetFromMap(new ConcurrentHashMap<>(16));

	/**
	 * Names of beans currently excluded from in creation checks.
	 */
	private final Set<String> inCreationCheckExclusions =
			Collections.newSetFromMap(new ConcurrentHashMap<>(16));

	/**
	 * Collection of suppressed Exceptions, available for associating related causes.
	 * 存放异常出现的相关的原因的集合
	 */
	@Nullable
	private Set<Exception> suppressedExceptions;

	/**
	 * Flag that indicates whether we're currently within destroySingletons. 标志，指示我们目前是否在销毁单例中
	 */
	private boolean singletonsCurrentlyInDestruction = false;

	/**
	 * Disposable bean instances: bean name to disposable instance. 存放一次性bean的缓存
	 */
	private final Map<String, Object> disposableBeans = new LinkedHashMap<>();

	/**
	 * Map between containing bean names: bean name to Set of bean names that the bean contains.
	 * 外部bean与被包含在外部bean的所有内部bean集合包含关系的缓存
	 */
	private final Map<String, Set<String>> containedBeanMap = new ConcurrentHashMap<>(16);

	/**
	 * Map between dependent bean names: bean name to Set of dependent bean names.
	 * 指定bean与依赖指定bean的所有bean的依赖关系的缓存
	 */
	private final Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);

	/**
	 * Map between depending bean names: bean name to Set of bean names for the bean's dependencies.
	 * 指定bean与创建这个bean所需要依赖的所有bean的依赖关系的缓存
	 */
	private final Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);


	// SingletonBeanRegistry接口的registerSingleton方法的实现  注册bean
	@Override
	public void registerSingleton(String beanName, Object singletonObject)
			throws IllegalStateException {
		Assert.notNull(beanName, "Bean name must not be null");
		Assert.notNull(singletonObject, "Singleton object must not be null");
		synchronized (this.singletonObjects) {
			Object oldObject = this.singletonObjects.get(beanName);
			//如果singletonObjects缓存找到有指定名称为beanName的对象，则表示该名称已被占用
			if (oldObject != null) {
				throw new IllegalStateException("Could not register object [" + singletonObject +
						"] under bean name '" + beanName + "': there is already object ["
						+ oldObject + "] bound");
			}
			addSingleton(beanName, singletonObject);
		}
	}

	/**
	 * Add the given singleton object to the singleton cache of this factory.
	 * <p>To be called for eager registration of singletons.
	 *
	 * @param beanName        the name of the bean
	 * @param singletonObject the singleton object // 注册bean
	 */
	protected void addSingleton(String beanName, Object singletonObject) {
		synchronized (this.singletonObjects) {
			//bean 放入singleton对象map
			this.singletonObjects.put(beanName, singletonObject);
			// beanName已被注册存放在singletonObjects缓存，那么singletonFactories不应该再持有名称为beanName的工厂
			this.singletonFactories.remove(beanName);
			// beanName已被注册存放在singletonObjects缓存，那么earlySingletonObjects 早期 不应该再持有名称为beanName的bean。
			this.earlySingletonObjects.remove(beanName);
			// beanName放进单例注册表中
			this.registeredSingletons.add(beanName);
		}
	}

	/**
	 * Add the given singleton factory for building the specified singleton if necessary.
	 * <p>To be called for eager registration of singletons, e.g. to be able to
	 * resolve circular references.
	 *
	 * @param beanName         the name of the bean
	 * @param singletonFactory the factory for the singleton object 添加 名称为beanName的singletonFactory对象
	 */
	protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
		Assert.notNull(singletonFactory, "Singleton factory must not be null");
		// 锁定 singleton对象的缓存
		synchronized (this.singletonObjects) {
			// 只有不存在才执行
			if (!this.singletonObjects.containsKey(beanName)) {
				this.singletonFactories.put(beanName, singletonFactory);
				this.earlySingletonObjects.remove(beanName);
				this.registeredSingletons.add(beanName);
			}
		}
	}

	/**
	 * SingletonBeanRegistry接口的getSingleton方法的实现
	 *
	 * @param beanName the name of the bean to look for
	 * @return
	 */
	@Override
	@Nullable
	public Object getSingleton(String beanName) {
		return getSingleton(beanName, true);
	}

	/**
	 * Return the (raw) singleton object registered under the given name.
	 * <p>Checks already instantiated singletons and also allows for an early
	 * reference to a currently created singleton (resolving a circular reference).
	 *
	 * @param beanName            the name of the bean to look for
	 * @param allowEarlyReference whether early references should be created or not
	 * @return the registered singleton object, or {@code null} if none found
	 */
	@Nullable
	protected Object getSingleton(String beanName, boolean allowEarlyReference) {
		// Quick check for existing instance without full singleton lock
		Object singletonObject = this.singletonObjects.get(beanName);
		// 如果singletonObjects指定beanName的对象是不存在的  并且正在创建bean集合中也不包含
		if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
			//从早期map中查找
			singletonObject = this.earlySingletonObjects.get(beanName);
			// 早期bean map中不存在 且允许早期引用 为true  默认为true
			if (singletonObject == null && allowEarlyReference) {
				synchronized (this.singletonObjects) {
					// Consistent creation of early reference within full singleton lock
					singletonObject = this.singletonObjects.get(beanName);
					// 再次从获取singletonObjects
					if (singletonObject == null) {
						//从早期map中查找
						singletonObject = this.earlySingletonObjects.get(beanName);
						if (singletonObject == null) {
							//获取bean 工厂
							ObjectFactory<?> singletonFactory = this.singletonFactories
									.get(beanName);
							if (singletonFactory != null) {
								// 如果工厂不为空 从singletonFactory创建指定的单例对象
								singletonObject = singletonFactory.getObject();
								// 这里可以看出earlySingletonObjects 早期缓存应该是存放singletonFactory产生的singleton
								this.earlySingletonObjects.put(beanName, singletonObject);
								// 这里表示指定的beanName已被占用，所以要在singletonFactories移除该名称
								this.singletonFactories.remove(beanName);
							}
						}
					}
				}
			}
		}
		return singletonObject;
	}

	/**
	 * Return the (raw) singleton object registered under the given name, creating and registering a
	 * new one if none registered yet.
	 *
	 * @param beanName         the name of the bean
	 * @param singletonFactory the ObjectFactory to lazily create the singleton with, if necessary
	 * @return the registered singleton object
	 */
	public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
		Assert.notNull(beanName, "Bean name must not be null");
		// 锁定
		synchronized (this.singletonObjects) {
			Object singletonObject = this.singletonObjects.get(beanName);
			// 如果singetonObjects缓存不存在名称为beanName的对象
			if (singletonObject == null) {
				// 是否正在销毁singellton
				if (this.singletonsCurrentlyInDestruction) {
					throw new BeanCreationNotAllowedException(beanName,
							"Singleton bean creation not allowed while singletons of this factory are in destruction "
									+
									"(Do not request a bean from a BeanFactory in a destroy method implementation!)");
				}
				if (logger.isDebugEnabled()) {
					logger.debug("Creating shared instance of singleton bean '" + beanName + "'");
				}
				// 单例对象创建前的回调,默认实现注册正在创建的单例
				beforeSingletonCreation(beanName);
				boolean newSingleton = false;
				// 判断存储异常相关原因的集合是否已存在
				boolean recordSuppressedExceptions = (this.suppressedExceptions == null);
				// 不存在初始化
				if (recordSuppressedExceptions) {
					this.suppressedExceptions = new LinkedHashSet<>();
				}
				try {
					// 由参数给定的singletonFactory创建singleton对象,getObject方法的具体实现由ObjectFactory的子类决定
					singletonObject = singletonFactory.getObject();
					newSingleton = true;
				} catch (IllegalStateException ex) {
					// Has the singleton object implicitly appeared in the meantime ->
					// if yes, proceed with it since the exception indicates that state.

					singletonObject = this.singletonObjects.get(beanName);
					if (singletonObject == null) {
						throw ex;
					}
				} catch (BeanCreationException ex) {
					// 如果异常被抓取，在这里将出现异常的原因抛出
					if (recordSuppressedExceptions) {
						for (Exception suppressedException : this.suppressedExceptions) {
							ex.addRelatedCause(suppressedException);
						}
					}
					throw ex;
				} finally {
					// 结束前，将异常集合销毁掉
					if (recordSuppressedExceptions) {
						this.suppressedExceptions = null;
					}
					// 单例创建之后的回调,默认的实现标志单例不要在创建了。
					afterSingletonCreation(beanName);
				}
				if (newSingleton) {
					// 注册创建后的单例
					addSingleton(beanName, singletonObject);
				}
			}
			return singletonObject;
		}
	}

	/**
	 * Register an exception that happened to get suppressed during the creation of a singleton bean
	 * instance, e.g. a temporary circular reference resolution problem.
	 * <p>The default implementation preserves any given exception in this registry's
	 * collection of suppressed exceptions, up to a limit of 100 exceptions, adding them as related
	 * causes to an eventual top-level {@link BeanCreationException}.
	 *
	 * @param ex the Exception to register
	 * @see BeanCreationException#getRelatedCauses() 注册 发生在singeton bean 实例创建之间发生的异常
	 */
	protected void onSuppressedException(Exception ex) {
		synchronized (this.singletonObjects) {
			if (this.suppressedExceptions != null
					&& this.suppressedExceptions.size() < SUPPRESSED_EXCEPTIONS_LIMIT) {
				this.suppressedExceptions.add(ex);
			}
		}
	}

	/**
	 * Remove the bean with the given name from the singleton cache of this factory, to be able to
	 * clean up eager registration of a singleton if creation failed. 移除名称为beanName的单例,主要在四个集合中移除
	 *
	 * @param beanName the name of the bean
	 * @see #getSingletonMutex()
	 */
	protected void removeSingleton(String beanName) {
		synchronized (this.singletonObjects) {
			this.singletonObjects.remove(beanName);
			this.singletonFactories.remove(beanName);
			this.earlySingletonObjects.remove(beanName);
			this.registeredSingletons.remove(beanName);
		}
	}

	@Override
	public boolean containsSingleton(String beanName) {
		return this.singletonObjects.containsKey(beanName);
	}

	/**
	 * 获取所有bean name
	 *
	 * @return
	 */
	@Override
	public String[] getSingletonNames() {
		synchronized (this.singletonObjects) {
			return StringUtils.toStringArray(this.registeredSingletons);
		}
	}

	/**
	 * 获取所有bean 数量
	 *
	 * @return
	 */
	@Override
	public int getSingletonCount() {
		synchronized (this.singletonObjects) {
			return this.registeredSingletons.size();
		}
	}

	/**
	 * @param beanName
	 * @param inCreation
	 */
	public void setCurrentlyInCreation(String beanName, boolean inCreation) {
		Assert.notNull(beanName, "Bean name must not be null");
		if (!inCreation) {
			this.inCreationCheckExclusions.add(beanName);
		} else {
			this.inCreationCheckExclusions.remove(beanName);
		}
	}

	public boolean isCurrentlyInCreation(String beanName) {
		Assert.notNull(beanName, "Bean name must not be null");
		return (!this.inCreationCheckExclusions.contains(beanName) && isActuallyInCreation(
				beanName));
	}

	protected boolean isActuallyInCreation(String beanName) {
		return isSingletonCurrentlyInCreation(beanName);
	}

	/**
	 * Return whether the specified singleton bean is currently in creation (within the entire
	 * factory).
	 *
	 * @param beanName the name of the bean 返回 存放正在创建单例的集合是否包含指定名称为beanName的单例存在
	 */
	public boolean isSingletonCurrentlyInCreation(String beanName) {
		return this.singletonsCurrentlyInCreation.contains(beanName);
	}

	/**
	 * Callback before singleton creation.
	 * <p>The default implementation register the singleton as currently in creation.
	 *
	 * @param beanName the name of the singleton about to be created
	 * @see #isSingletonCurrentlyInCreation 单例对象创建前的回调,默认实现singletonsCurrentlyInCreation集合注册正在创建的单例
	 */
	protected void beforeSingletonCreation(String beanName) {
		if (!this.inCreationCheckExclusions.contains(beanName)
				&& !this.singletonsCurrentlyInCreation.add(beanName)) {
			throw new BeanCurrentlyInCreationException(beanName);
		}
	}

	/**
	 * Callback after singleton creation.
	 * <p>The default implementation marks the singleton as not in creation anymore.
	 *
	 * @param beanName the name of the singleton that has been created
	 * @see #isSingletonCurrentlyInCreation 单例创建之后的回调,默认实现singletonCurrentlyInCreation集合移除正在创建的单例
	 */
	protected void afterSingletonCreation(String beanName) {
		if (!this.inCreationCheckExclusions.contains(beanName)
				&& !this.singletonsCurrentlyInCreation.remove(beanName)) {
			throw new IllegalStateException(
					"Singleton '" + beanName + "' isn't currently in creation");
		}
	}


	/**
	 * Add the given bean to the list of disposable beans in this registry.
	 * <p>Disposable beans usually correspond to registered singletons,
	 * matching the bean name but potentially being a different instance (for example, a
	 * DisposableBean adapter for a singleton that does not naturally implement Spring's
	 * DisposableBean interface).
	 *
	 * @param beanName the name of the bean
	 * @param bean     the bean instance 一次性bean注册，存放在disponsableBeans集合中
	 */
	public void registerDisposableBean(String beanName, DisposableBean bean) {
		synchronized (this.disposableBeans) {
			this.disposableBeans.put(beanName, bean);
		}
	}

	/**
	 * Register a containment relationship between two beans, e.g. between an inner bean and its
	 * containing outer bean.
	 * <p>Also registers the containing bean as dependent on the contained bean
	 * in terms of destruction order.
	 *
	 * @param containedBeanName  the name of the contained (inner) bean
	 * @param containingBeanName the name of the containing (outer) bean
	 * @see #registerDependentBean 注册两个bean之间的控制关系,例如内部bean和包含其的外部bean之间
	 */
	public void registerContainedBean(String containedBeanName, String containingBeanName) {
		synchronized (this.containedBeanMap) {
			Set<String> containedBeans =
					this.containedBeanMap
							.computeIfAbsent(containingBeanName, k -> new LinkedHashSet<>(8));
			if (!containedBeans.add(containedBeanName)) {
				return;
			}
		}
		registerDependentBean(containedBeanName, containingBeanName);
	}

	/**
	 * Register a dependent bean for the given bean, to be destroyed before the given bean is
	 * destroyed.
	 * 注册给定bean的一个依赖bean，给定的bean销毁之前被销毁
	 * @param beanName          the name of the bean
	 * @param dependentBeanName the name of the dependent bean 注册给定bean的一个依赖bean，给定的bean销毁之前被销毁
	 */
	public void registerDependentBean(String beanName, String dependentBeanName) {
		String canonicalName = canonicalName(beanName);

		synchronized (this.dependentBeanMap) {
			Set<String> dependentBeans =
					this.dependentBeanMap
							.computeIfAbsent(canonicalName, k -> new LinkedHashSet<>(8));
			if (!dependentBeans.add(dependentBeanName)) {
				return;
			}
		}

		synchronized (this.dependenciesForBeanMap) {
			Set<String> dependenciesForBean =
					this.dependenciesForBeanMap
							.computeIfAbsent(dependentBeanName, k -> new LinkedHashSet<>(8));
			dependenciesForBean.add(canonicalName);
		}
	}

	/**
	 * Determine whether the specified dependent bean has been registered as dependent on the given
	 * bean or on any of its transitive dependencies.
	 *
	 * @param beanName          the name of the bean to check
	 * @param dependentBeanName the name of the dependent bean
	 * @since 4.0
	 */
	protected boolean isDependent(String beanName, String dependentBeanName) {
		synchronized (this.dependentBeanMap) {
			return isDependent(beanName, dependentBeanName, null);
		}
	}

	private boolean isDependent(String beanName, String dependentBeanName,
			@Nullable Set<String> alreadySeen) {
		if (alreadySeen != null && alreadySeen.contains(beanName)) {
			return false;
		}
		String canonicalName = canonicalName(beanName);
		Set<String> dependentBeans = this.dependentBeanMap.get(canonicalName);
		if (dependentBeans == null) {
			return false;
		}
		if (dependentBeans.contains(dependentBeanName)) {
			return true;
		}
		for (String transitiveDependency : dependentBeans) {
			if (alreadySeen == null) {
				alreadySeen = new HashSet<>();
			}
			alreadySeen.add(beanName);
			if (isDependent(transitiveDependency, dependentBeanName, alreadySeen)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determine whether a dependent bean has been registered for the given name.
	 * 确定是否还存在名为beanName的被依赖关系
	 * @param beanName the name of the bean to check
	 */
	protected boolean hasDependentBean(String beanName) {
		return this.dependentBeanMap.containsKey(beanName);
	}

	/**
	 * Return the names of all beans which depend on the specified bean, if any.
	 * 返回依赖于指定的bean的所有bean的名称，如果有的话
	 * @param beanName the name of the bean
	 * @return the array of dependent bean names, or an empty array if none
	 */
	public String[] getDependentBeans(String beanName) {
		Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
		if (dependentBeans == null) {
			return new String[0];
		}
		synchronized (this.dependentBeanMap) {
			return StringUtils.toStringArray(dependentBeans);
		}
	}

	/**
	 * Return the names of all beans that the specified bean depends on, if any.
	 * 返回指定的bean依赖于所有的bean的名称，如果有的话。
	 * @param beanName the name of the bean
	 * @return the array of names of beans which the bean depends on, or an empty array if none
	 */
	public String[] getDependenciesForBean(String beanName) {
		Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(beanName);
		if (dependenciesForBean == null) {
			return new String[0];
		}
		synchronized (this.dependenciesForBeanMap) {
			return StringUtils.toStringArray(dependenciesForBean);
		}
	}

	public void destroySingletons() {
		if (logger.isTraceEnabled()) {
			logger.trace("Destroying singletons in " + this);
		}
		synchronized (this.singletonObjects) {
			this.singletonsCurrentlyInDestruction = true;
		}

		String[] disposableBeanNames;
		synchronized (this.disposableBeans) {
			disposableBeanNames = StringUtils.toStringArray(this.disposableBeans.keySet());
		}
		for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
			destroySingleton(disposableBeanNames[i]);
		}

		this.containedBeanMap.clear();
		this.dependentBeanMap.clear();
		this.dependenciesForBeanMap.clear();

		clearSingletonCache();
	}

	/**
	 * Clear all cached singleton instances in this registry.
	 *
	 * @since 4.3.15
	 */
	protected void clearSingletonCache() {
		synchronized (this.singletonObjects) {
			this.singletonObjects.clear();
			this.singletonFactories.clear();
			this.earlySingletonObjects.clear();
			this.registeredSingletons.clear();
			this.singletonsCurrentlyInDestruction = false;
		}
	}

	/**
	 * Destroy the given bean. Delegates to {@code destroyBean} if a corresponding disposable bean
	 * instance is found.
	 *
	 * @param beanName the name of the bean
	 * @see #destroyBean
	 */
	public void destroySingleton(String beanName) {
		// Remove a registered singleton of the given name, if any.
		removeSingleton(beanName);

		// Destroy the corresponding DisposableBean instance.
		DisposableBean disposableBean;
		synchronized (this.disposableBeans) {
			disposableBean = (DisposableBean) this.disposableBeans.remove(beanName);
		}
		destroyBean(beanName, disposableBean);
	}

	/**
	 * Destroy the given bean. Must destroy beans that depend on the given bean before the bean
	 * itself. Should not throw any exceptions.
	 *  销毁单例
	 * @param beanName the name of the bean
	 * @param bean     the bean instance to destroy
	 */
	protected void destroyBean(String beanName, @Nullable DisposableBean bean) {
		// Trigger destruction of dependent beans first...

		Set<String> dependencies;
		synchronized (this.dependentBeanMap) {
			// Within full synchronization in order to guarantee a disconnected Set
			// 这段代码告诉我们先移除要销毁依赖bean  移除依赖关系
			dependencies = this.dependentBeanMap.remove(beanName);
		}
		if (dependencies != null) {
			if (logger.isTraceEnabled()) {
				logger.trace(
						"Retrieved dependent beans for bean '" + beanName + "': " + dependencies);
			}
			for (String dependentBeanName : dependencies) {
				destroySingleton(dependentBeanName);
			}
		}

		// Actually destroy the bean now...
		//  销毁Bean实例
		if (bean != null) {
			try {
				bean.destroy();
			} catch (Throwable ex) {
				if (logger.isWarnEnabled()) {
					logger.warn(
							"Destruction of bean with name '" + beanName + "' threw an exception",
							ex);
				}
			}
		}

		// Trigger destruction of contained beans...
		//从containedBeanMap缓存中移除要销毁的bean，递归移除它的包含内部bean集合
		Set<String> containedBeans;
		synchronized (this.containedBeanMap) {
			// Within full synchronization in order to guarantee a disconnected Set
			containedBeans = this.containedBeanMap.remove(beanName);
		}
		if (containedBeans != null) {
			for (String containedBeanName : containedBeans) {
				destroySingleton(containedBeanName);
			}
		}

		// Remove destroyed bean from other beans' dependencies.
		// 从其它bean的依赖bean集合中移除要销毁的bean
		synchronized (this.dependentBeanMap) {
			for (Iterator<Map.Entry<String, Set<String>>> it = this.dependentBeanMap.entrySet()
					.iterator(); it.hasNext(); ) {
				Map.Entry<String, Set<String>> entry = it.next();
				Set<String> dependenciesToClean = entry.getValue();
				dependenciesToClean.remove(beanName);
				if (dependenciesToClean.isEmpty()) {
					it.remove();
				}
			}
		}

		// Remove destroyed bean's prepared dependency information.
		// 最后 从dependenciesForBeanMap缓存中移除要销毁的bean
		this.dependenciesForBeanMap.remove(beanName);
	}

	/**
	 * Exposes the singleton mutex to subclasses and external collaborators.
	 * <p>Subclasses should synchronize on the given Object if they perform
	 * any sort of extended singleton creation phase. In particular, subclasses should <i>not</i>
	 * have their own mutexes involved in singleton creation, to avoid the potential for deadlocks
	 * in lazy-init situations.
	 */
	@Override
	public final Object getSingletonMutex() {
		return this.singletonObjects;
	}

}
