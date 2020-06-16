package com.nepxion.discovery.plugin.strategy.starter.agent.plugin.discovery.zuul;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import java.security.ProtectionDomain;

import com.nepxion.discovery.plugin.strategy.starter.agent.logger.AgentLogger;
import com.nepxion.discovery.plugin.strategy.starter.agent.matcher.ClassMatcher;
import com.nepxion.discovery.plugin.strategy.starter.agent.matcher.Matchers;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.Plugin;
import com.nepxion.discovery.plugin.strategy.starter.agent.threadlocal.ThreadLocalCopier;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformCallback;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformTemplate;

public class DiscoveryZuulPlugin extends Plugin {
    private static final AgentLogger LOG = AgentLogger.getLogger(DiscoveryZuulPlugin.class.getName());

    public DiscoveryZuulPlugin(TransformTemplate transformTemplate) {
        super(transformTemplate);
    }

    @Override
    public void install() {
        ClassMatcher classMatcher = Matchers.newClassNameMatcher("com.nepxion.discovery.plugin.strategy.zuul.context.ZuulStrategyContext");
        transformTemplate.transform(classMatcher, new TransformCallback() {
            @Override
            public byte[] doInTransform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                ThreadLocalCopier.register(new ZuulStrategyContextHook());

                return null;
            }
        });
        LOG.info(String.format("%s install successfully", this.getClass().getSimpleName()));
    }
}