package ${basepackage}.cfg;

import ${basepackage}.domain.SearchField;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.lang.annotation.Annotation;

/**
 * Created by BIG on 2016/6/2.
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {
        MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MybatisAppExecutor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // TODO Auto-generated method stub
        Object[] parameters = invocation.getArgs();
        if (parameters != null && parameters.length >= 1) {


            Object parameter = parameters[1];

            if (parameter instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) parameter;

                // 如果参数中含有
                if (map.containsKey("condition")) {
                    Map<String, Object> p = (Map<String, Object>) map.get("condition");
                    if (p != null) {
                        Map<String, Object> hMap = new HashMap<>();
                        for (Map.Entry<String, Object> entry : p.entrySet()) {

                            if (entry.getValue() instanceof SearchField) {
                                SearchField sf = (SearchField) entry.getValue();
//								System.out.println("field= " + sf.getField());
                                hMap.put(entry.getKey() + "__", true);
                            }
                        }
                        if (hMap.size() > 0) {
                            p.putAll(hMap);
                        }
                    }
                }
            }

        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // TODO Auto-generated method stub
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub

    }
}
