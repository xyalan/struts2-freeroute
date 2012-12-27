package org.apache.struts2.freeroute;

import org.apache.struts2.freeroute.annotation.MethodType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 路由工具类
 *
 * @author bastengao
 * @date 12-12-18 23:25
 */
public class RouteUtil {

    //路径变量正则 "/{([a-zA-Z]+)}"
    public static final Pattern PATH_VARIABLE_PATTERN = Pattern.compile("/\\{([a-zA-Z]+)\\}");

    private RouteUtil() {
    }

    /**
     * 将路由解析为 action 信息
     *
     * 如果是静态路由 "/persons/new" 将变成 "/persons/new#method"
     * 如果是动态路由 "/persons/{id}" 将变成 "/persons/__id__#method"
     *
     * @param routeMapping
     * @return
     */
    public static ActionInfo routeToAction(RouteMapping routeMapping) {
        String routePath = routeMapping.getRoute().value();
        routePath = ActionUtil.padSlash(routePath);
        routePath = RouteUtil.flatRoutePath(routePath);

        String namespace = ActionUtil.namespace(routePath);
        String actionName = ActionUtil.actionName(routePath);
        actionName = actionName + "#" + routeMapping.getMethod().getName();
        return new ActionInfo(namespace, actionName);
    }

    /**
     * 是否有 pathVariable
     *
     * @param routePath
     * @return
     */
    public static boolean hasPathVariables(String routePath) {
        Matcher matcher = PATH_VARIABLE_PATTERN.matcher(routePath);
        return matcher.find();
    }

    /**
     * 将路由路径转化为能够匹配此路径请求的正则表达式
     * 如：
     * "/persons/{id}" => "/persons/([a-zA-Z0-9]+)"
     * "/persons/{id}/edit/{name}" => "/persons/([a-zA-Z0-9]+)/edit/([a-zA-Z0-9]+)"
     * <p/>
     * TODO 目前只匹配字母和数字
     *
     * @param routePath
     * @return
     */
    public static String toRoutePathPattern(String routePath) {
        List<String> variableNames = pathVariableNames(routePath);
        String result = routePath;
        for (String variableName : variableNames) {
            result = PATH_VARIABLE_PATTERN.matcher(result).replaceFirst("/([a-zA-Z0-9]+)");
        }
        return result;
    }

    /**
     * 返回路由中的变量名
     *
     * @param routePath
     * @return
     */
    public static List<String> pathVariableNames(String routePath) {
        Matcher matcher = PATH_VARIABLE_PATTERN.matcher(routePath);
        List<String> variableNames = new ArrayList<String>();
        while (matcher.find()) {
            variableNames.add(matcher.group(1));
        }
        return variableNames;
    }

    /**
     * 如果 routePath 中有 pathVariable,
     * 例如 "/persons/{id}" 那么将路由转化为 "/persons/__id__"
     * "/persons/{id}/edit" 转化为 "/persons/__id__/edit"
     *
     * @param routePath
     * @return
     */
    public static String flatRoutePath(String routePath) {
        //TODO 这里用一种比较简单的办法
        routePath = routePath.replaceAll("\\{", "__");
        routePath = routePath.replaceAll("}", "__");
        return routePath;
    }

    /**
     * 根据 http method 返回对应的枚举
     * 如果没有匹配的返回 MethodType.NONE
     *
     * @param method
     * @return
     */
    public static MethodType valueOfMethod(String method) {
        try {
            return MethodType.valueOf(method.toUpperCase());
        } catch (IllegalArgumentException e) {
            return MethodType.NONE;
        }
    }
}