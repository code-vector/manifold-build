package com.lanyine.manifold.mybatis.interceptor;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.lanyine.manifold.mybatis.MapperFactory;
import com.lanyine.manifold.mybatis.ResultMapper;
import com.lanyine.manifold.mybatis.query.ParamsQuery;
import com.lanyine.manifold.mybatis.query.ParmasHelper;

/**
 * @Description: 拦截Mybatis Exutor,如果参数为ParmasQuery,如果未设置entityClass,则自动设置
 * @author shadow
 */
@Intercepts({
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }),
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class MybatisExcutorInterceptor implements Interceptor {

	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
		if (invocation.getArgs().length > 1) {
			Object parameter = invocation.getArgs()[1];
			if (parameter instanceof ParamsQuery) {
				ParamsQuery pq = (ParamsQuery) parameter;
				if (pq.getEntityClass() == null) {
					ResultMapper rm = getResultMapper(ms);
					ParmasHelper.setEntity(pq, rm.getEntity());
				}
			}
		}
		return invocation.proceed();
	}

	private ResultMapper getResultMapper(MappedStatement ms) {
		String name = ms.getId();
		String mapper = name.substring(0, name.lastIndexOf("."));
		return MapperFactory.fromMapper(mapper);
	}

	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	public void setProperties(Properties properties) {
	}
}