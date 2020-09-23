import cliente.UDPListaContactos;
import cliente.models.ClienteDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UDPListaContactosTest {
    @Test
    public void usageTest() throws ExecutionException, InterruptedException {
        final Integer port = 9999;
        final String ip = "localhost";


        Integer numberOfThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);


    }
}
