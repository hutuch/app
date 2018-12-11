package liu.chi.app.gateway.rpc;

import io.grpc.BindableService;
import io.grpc.ServerServiceDefinition;
import liu.chi.grpc.starter.GrpcService;

/**
 * @author liuchi
 * @date 2018-12-11 10:39
 */
@GrpcService(name = "hello")
public class HelloGrpcService implements BindableService {
    @Override
    public ServerServiceDefinition bindService() {
        return ServerServiceDefinition.builder("hello").build();
    }
}
