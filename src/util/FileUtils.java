// Import File Class for file operations
import java.io.File;
import java.io.IOException;

public class FileUtils
{
    // Singleton design pattern impelementation.
    private static fileUtils singleton;
    
    private fileUtils() {}
    
    public static fileUtils getInstance()
    {
        if(singleton == null)
        {
            singleton = new fileUtils;
        }
        return fileUtils
    }
    
    // FileUtils functions
    // Finds input txt files and their paths
    public List<String> findTxtFiles()
    {
        private static inputPath = "D:\\projects\\JAVA\\multithread-file-analyzer\\input"
        try
        {
            File f = new File(inputPath);
            File[] matchingFiles = f.listFiles(new FilenameFilter()
            {
                public boolean accept(File dir, String name)
                {
                    return name.endsWith("txt");
                }
            });
        }
        // if input files not found or can't reached
        catch(IOException e)
        {
            System.err.println("Could not found files!");
        }
    }
    
    // Delete input files
    public void deleteInputs(List<String> fileList)
    {
        List<Path> paths = filePaths.stream()
                .map(Paths::get)
                .filter(Files::exists)
                .collect(Collectors.toList());
        
        paths.forEach(path ->
        {
            // if file exists then delete
            try
            {
                Files.delete(path);
                System.out.println("Deleted: " + path);
            }
            // if not found raise error
            catch (IOException e)
            {
                System.err.println("Could not delete: " + path);
            }
        });
    }
}