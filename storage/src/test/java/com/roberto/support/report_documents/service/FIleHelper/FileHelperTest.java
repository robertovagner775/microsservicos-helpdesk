package com.roberto.support.report_documents.service.FIleHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelperTest {

    public static File criarArquivoTemporario() throws IOException {
        // Cria um arquivo temporário
        File tempFile = File.createTempFile("teste-arquivo", ".txt");

        // Escreve conteúdo no arquivo
        FileWriter writer = new FileWriter(tempFile);
        writer.write("Conteúdo do arquivo para teste");
        writer.close();

        // O arquivo será deletado ao encerrar o teste/jvm (opcional)
        tempFile.deleteOnExit();

        return tempFile;
    }
}
