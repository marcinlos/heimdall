package pl.edu.agh.heimdall.loader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HeimdallClassLoader extends ClassLoader {

    public HeimdallClassLoader(ClassLoader parent) {
        super(parent);
    }
    
    private byte[] readClassDef(String name) throws IOException {
        String path = name.replace('.', '/') + ".class";
        InputStream input = getResourceAsStream(path);
        return readStream(input);
    }
    
    private byte[] readStream(InputStream input) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int read;
        while ((read = input.read(buffer)) > 0) {
            out.write(buffer, 0, read);
        }
        return out.toByteArray();
    }
    
    @Override
    public Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        
        synchronized (getClassLoadingLock(name)) {
            System.out.print("Requested " + name + "... ");
            Class<?> loaded = findLoadedClass(name);
            
            if (loaded != null) {
                System.out.println("[already loaded]");
                return loaded;
            }
            
            if (name.startsWith("java.")) {
                System.out.println("[skipping]");
                return super.loadClass(name, resolve);
            }
            try {
                System.out.println("[loading]");
                byte[] data = readClassDef(name);
                byte[] bytes = data;// transform
                
                return defineClass(name, bytes, 0, bytes.length);
            } catch (Exception e) {
                System.out.println("Error, falling back to parent (" + e + ")");
                return super.loadClass(name, resolve);
            }
        }
        
    }

}
