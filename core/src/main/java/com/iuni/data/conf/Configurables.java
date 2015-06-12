package com.iuni.data.conf;

import com.iuni.data.Context;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Configurables {

    /**
     * 使用上下文配置
     *
     * @param target
     * @param context
     * @return
     */
    public static boolean configure(Object target, Context context) {
        if (target instanceof Configurable) {
            ((Configurable) target).configure(context);
            return true;
        }

        return false;
    }

    /**
     * 使得配置类配置
     *
     * @param target
     * @param conf
     * @return
     */
    public static boolean configure(Object target, ComponentConfiguration conf) {
        if (target instanceof ConfigurableComponent) {
            ((ConfigurableComponent) target).configure(conf);
            return true;
        }
        return false;
    }

    /**
     * 确保上下文中key存在且其值不为空
     *
     * @param context
     * @param keys
     */
    public static void ensureRequiredNonNull(Context context, String... keys) {
        for (String key : keys) {
            if (!context.getParameters().containsKey(key)
                    || context.getParameters().get(key) == null) {
                throw new IllegalArgumentException("Required parameter " + key + " must exist and may not be null");
            }
        }
    }

}
