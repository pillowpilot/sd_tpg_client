package cliente.messageVisitors;

import cliente.messages.responses.AvailableContactsPayload;
import cliente.messages.responses.RegistrationAttemptPayload;
import cliente.messages.responses.Response;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Procesador de respuestas como visitante de respuestas.
 * Administra su propio hilo.
 */
public class ResponseProcessor implements Runnable, ResponseVisitor {
    private AtomicBoolean keepRunning;
    private BlockingQueue<Response> pendingResponses;

    public ResponseProcessor(@NotNull BlockingQueue<Response> pendingResponses) {
        this.keepRunning = new AtomicBoolean(true);
        this.pendingResponses = pendingResponses;
    }

    @Override
    public void visitAvailableContacts(Response response, AvailableContactsPayload payload) {
        System.out.println("In ResponseProcessor");
        System.out.println("response="+response);
        System.out.println("payload="+payload);
    }

    @Override
    public void visitRegistrationAttempt(Response response, RegistrationAttemptPayload payload) {
        System.out.println("In ResponseProcessor");
        System.out.println("response="+response);
        System.out.println("payload="+payload);
    }

    @Override
    public void run() {
        while(keepRunning.get()) {
            try {
                Response response = pendingResponses.take();
                System.out.println("Processing response: " + response);
                response.accept(this);
                System.out.println("Done.");
            } catch (InterruptedException exception) {
                exception.printStackTrace();
                keepRunning.set(false);
            }
        }
    }

    public synchronized void stop() {
        keepRunning.set(false);
    }
}
