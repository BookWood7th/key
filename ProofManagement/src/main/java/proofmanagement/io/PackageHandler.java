package proofmanagement.io;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipException;

import org.key_project.util.java.IOUtil;


/**
 * This class serves as an extractor to get the paths of specific files inside a proof bundle
 * (a zip containing possibly multiple *.proof/*.key files and corresponding source/classpath
 * files in a well defined directory structure).
 */
public class PackageHandler {
    /**
     * A matcher matches *.proof files.
     */
    private static final PathMatcher PROOF_MATCHER =
            FileSystems.getDefault().getPathMatcher("glob:**.proof");

    /**
     * A matcher matches *.key files.
     */
    private static final PathMatcher KEY_MATCHER =
            FileSystems.getDefault().getPathMatcher("glob:**.key");

    /**
     * This matcher matches *.java files.
     */
    private static final PathMatcher SRC_MATCHER =
            FileSystems.getDefault().getPathMatcher("glob:**.java");

    /**
     * This matcher matches *.java, *.class, *.zip, and *.jar files.
     */
    private static final PathMatcher CLASSPATH_MATCHER =
            FileSystems.getDefault().getPathMatcher("glob:**.{java,class,zip,jar}");

    /**
     * This matcher matches *.java files.
     */
    private static final PathMatcher BOOTCLASSPATH_MATCHER =
            FileSystems.getDefault().getPathMatcher("glob:**.java");

    private Path zipPath;
    private boolean isInitialized = false;
    //private FileRepo fileRepo;
    private Path tmpDir;

    /**
     * Creates a new PackageHandler for the specified proof bundle.
     * @param zipPath the path of the proof bundle (zip file)
     */
    public PackageHandler(Path zipPath) {
        this.zipPath = zipPath;
    }

    private void load() throws ZipException, IOException {
        if (!isInitialized) {
            tmpDir = Files.createTempDirectory("KeYunzip");
            IOUtil.extractZip(zipPath, tmpDir);

            //fileRepo = new DiskFileRepo("HacKeYrepo");

            // point the FileRepo to the temporary directory
            //fileRepo.setBaseDir(tmpDir);

            isInitialized = true;
        }
    }

    private List<Path> getFiles(Path dir, PathMatcher matcher) throws IOException {
        List<Path> files = new ArrayList<>();
        if (Files.isDirectory(dir)) {
            files.addAll(Files.list(dir)
                    .filter(matcher::matches)
                    .collect(Collectors.toList()));
        }
        return files;
    }

    // *.proof
    public List<Path> getProofFiles() throws ZipException, IOException {
        // ensure the zip is extracted
        load();

        return getFiles(tmpDir, PROOF_MATCHER);
    }

    // *.key
    public List<Path> getKeYFiles() throws ZipException, IOException {
        // ensure the zip is extracted
        load();

        return getFiles(tmpDir, KEY_MATCHER);
    }

    // *.java
    public List<Path> getSourceFiles() throws ZipException, IOException {
        // ensure the zip is extracted
        load();

        Path srcPath = tmpDir.resolve(Paths.get("src"));
        return getFiles(srcPath, SRC_MATCHER);
    }

    // *.java, *.class, *.zip, *.jar
    public List<Path> getClasspathFiles() throws ZipException, IOException {
        // ensure the zip is extracted
        load();

        Path classpathPath = tmpDir.resolve(Paths.get("classpath"));
        return getFiles(classpathPath, CLASSPATH_MATCHER);
    }

    // *.java
    public List<Path> getBootclasspathFiles() throws ZipException, IOException {
        // ensure the zip is extracted
        load();

        Path bootclasspathPath = tmpDir.resolve(Paths.get("bootclasspath"));
        return getFiles(bootclasspathPath, BOOTCLASSPATH_MATCHER);
    }

    /*
    public FileRepo getFileRepo() {
        return fileRepo;
    }
    */

    public Path getDir() {
        return tmpDir;
    }


}
