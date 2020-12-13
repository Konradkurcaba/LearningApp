package pl.kurcaba.learn.helper.remote.backend.interceptors;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Arrays;

@LogParameters
@Interceptor
@Priority(100)
public class LogParametersImpl
{
    @AroundInvoke
    public Object manageTransaction(InvocationContext ctx) throws Exception
    {
        System.out.println("Method " + ctx.getMethod() + "Invoked with parameters" + Arrays.toString(ctx.getParameters()));
        return ctx.proceed();
    }

}

