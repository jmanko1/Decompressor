package compressionManager;

import exeRunner.ExeRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

/* Klasa odpowiadająca za kompresje plików .txt z wykorzystaniem zewnętrznego kompresora (plik wykonywalny)*/
public class Compressor {
    private final ExeRunner exeRunner;
    public Compressor() {
        /* Tworzymy obiekt uruchamiający pliki wykonywalne, ustawiamy go na kompresor */
        exeRunner = new ExeRunner("src\\compressor.exe");
    }

    /* Funkcja uruchamia zewnętrzny kompresor */
    public void compress(String filePath) throws IOException, InterruptedException {
        if (!(new File(filePath).isFile())) {
            throw new FileNotFoundException("Plik nie istnieje.");
        }

        if (!filePath.endsWith(".txt"))
            throw new InvalidFileExtensionException("Kompresor nie obsługuje pliku o tym rozszerzeniu.");

        exeRunner.run("-i " + filePath);

        switch (exeRunner.getExitCode()) {
            case 0 -> System.out.println("Poprawnie skompresowano");
            case 3 -> throw new FileNotFoundException("Plik nie istnieje");
            case 4 -> throw new AccessDeniedException("Brak uprawnień do pliku");
        }
    }
}
