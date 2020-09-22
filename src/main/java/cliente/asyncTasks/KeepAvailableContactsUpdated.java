package cliente.asyncTasks;

import java.util.TimerTask;

/**
 * TimerTask que mantiene la lista de contactos actualizada.
 * Crea su propio hilo para cada ejecución.
 * El periodo entre llamadas está especificado en Client.Client.
 */
public class KeepAvailableContactsUpdated extends TimerTask {
    @Override
    public void run() {
        System.out.println("Initializing timed task.");
        System.out.println(System.currentTimeMillis());
        System.out.println("Timed task ended.");
    }
}
