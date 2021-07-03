package Client.util;

import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import javax.imageio.*;

public class ImageLoader {
    private ExecutorService executor = Executors.newCachedThreadPool();

    public ImageLoader(List<URL> urls) {

    }

    public CompletableFuture<List<BufferedImage>> getImages(List<URL> urls) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                var result = new ArrayList<BufferedImage>();
                for (URL url : urls) {
                    result.add(ImageIO.read(url));
                    System.out.println("Loaded " + url);
                }
                return result;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }, executor);
    }

    public void saveImages(List<BufferedImage> images) {
        System.out.println("Saving " + images.size() + " images");
        try {
            for (int i = 0; i < images.size(); i++) {
                String filename = "D:/repos/Java Projects/vscode Examples/ThreadExample/tmp/image" + (i + 1) + ".png";
                ImageIO.write(images.get(i), "PNG", new File(filename));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        executor.shutdown();
    }
}
