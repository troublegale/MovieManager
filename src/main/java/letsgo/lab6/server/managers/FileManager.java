package letsgo.lab6.server.managers;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import letsgo.lab6.server.entities.Coordinates;
import letsgo.lab6.server.entities.Location;
import letsgo.lab6.server.entities.Movie;
import letsgo.lab6.server.entities.Person;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Scanner;

public class FileManager {

    private static String filePath;

    public static void setFilePath(String pathString) {
        filePath = pathString;
    }

    public static void writeCollectionIntoFile(CollectionManager collectionManager) {
        try {
            JAXBContext context = JAXBContext.newInstance(Movie.class, Person.class, Location.class,
                    Coordinates.class, CollectionManager.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            PrintWriter fileOutput = new PrintWriter(filePath);
            marshaller.marshal(collectionManager, fileOutput);
            System.out.println("Коллекция сохранена в файл.");

        } catch (JAXBException | IOException e) {
            System.out.println("Не удалось сохранить коллекцию в файл.");
            e.getMessage();
        }
    }

    public static CollectionManager readCollectionFromFile() {
        try {
            JAXBContext context = JAXBContext.newInstance(Movie.class, Person.class, Location.class,
                    Coordinates.class, CollectionManager.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Path path = Paths.get(filePath);
            Scanner scanner = new Scanner(path);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
            }
            if (sb.toString().isBlank()) {
                return new CollectionManager(new ArrayDeque<>());
            }
            StringReader sr = new StringReader(sb.toString());
            CollectionManager collectionManager = (CollectionManager) unmarshaller.unmarshal(sr);
            if (collectionManager.getMovieDeque() == null) {
                collectionManager.setMovieDeque(new ArrayDeque<>());
            }
            long uniqueIds = collectionManager.getMovieDeque().stream().mapToLong(Movie::getId).distinct().count();

            if (uniqueIds != collectionManager.getMovieDeque().size()) {
                throw new JAXBException("Одинаковые ID у нескольких фильмов.");
            }

            for (Movie movie : collectionManager.getMovieDeque()) {
                if (!movie.allFieldsValid()) {
                    throw new JAXBException("Неверные атрибуты фильма.");
                }
            }
            return collectionManager;

        } catch (JAXBException | IOException e) {
            System.out.println("Не удалось прочитать коллекцию из файла.");
            e.getMessage();
            System.out.println("Выход из программы.");
            System.exit(1);
            return null;
        }
    }
}
