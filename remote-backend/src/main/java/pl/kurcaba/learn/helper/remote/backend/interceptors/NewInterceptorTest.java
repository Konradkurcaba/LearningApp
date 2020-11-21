package pl.kurcaba.learn.helper.remote.backend.interceptors;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;


@LogParameters
@Interceptor
@Priority(200)
public class NewInterceptorTest
{
    @AroundInvoke
    public Object manageTransaction(InvocationContext ctx) throws Exception
    {
        System.out.println("Interceptor2");
        return ctx.proceed();
    }
}
