package org.example;

import com.example.grpc.GreetingServiceGrpc;
import com.example.grpc.GreetingServiceOuterClass;
import io.grpc.stub.StreamObserver;

/**
 *
 */
public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void greeting(GreetingServiceOuterClass.HelloRequest request,
                         StreamObserver<GreetingServiceOuterClass.HelloResponse> responseObserver) {
        System.out.println(request);
        for (int i = 0; i < 10_000; i++) {
            try {
                Thread.sleep(100);
                GreetingServiceOuterClass.HelloResponse response =
                        GreetingServiceOuterClass
                                .HelloResponse
                                .newBuilder()
                                .setGreeting("Hello from server " + request.getName())
                                .build();

                responseObserver.onNext(response);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        responseObserver.onCompleted();
    }
}
