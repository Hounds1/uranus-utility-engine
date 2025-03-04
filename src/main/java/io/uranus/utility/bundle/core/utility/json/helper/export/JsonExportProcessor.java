package io.uranus.utility.bundle.core.utility.json.helper.export;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.List;

public class JsonExportProcessor {

    private String exportPath;
    private String exportFileName;
    private final List<?> objects;

    protected JsonExportProcessor(List<?> objects) {
        this.objects = objects;
    }

    public JsonExportProcessor withExportPath(String exportPath) {
        this.exportPath = exportPath;
        return this;
    }

    public JsonExportProcessor withExportFileName(String exportFileName) {
        this.exportFileName = exportFileName;
        return this;
    }

    public void export() {
        if (this.exportPath == null || this.exportPath.isEmpty()) {
            throw new RuntimeException("exportPath cannot be null or empty");
        }

        if (this.exportFileName == null || this.exportFileName.isEmpty()) {
            throw new RuntimeException("exportFileName cannot be null or empty");
        }

        if (!this.exportFileName.endsWith(".json")) {
            throw new RuntimeException("exportFileName must end with .json");
        }

        if (this.objects.isEmpty()) {
            return;
        }

        File exportPath = new File(this.exportPath);
        if (!exportPath.exists()) {
            if (!exportPath.mkdirs()) {
                throw new RuntimeException("Unable to create directory " + exportPath);
            }
        }

        final String fullPath = this.exportPath + File.separator + this.exportFileName;

        try(FileOutputStream fos = new FileOutputStream(fullPath);
            JsonGenerator generator = new JsonFactory().createGenerator(fos)) {

            Object o = this.objects.get(0);
            Field[] fields = o.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
            }

            generator.writeStartArray();

            for (Object object : this.objects) {
                generator.writeStartObject();

                for (Field field : fields) {
                    generator.writeStringField(field.getName(), field.get(object).toString());
                }

                generator.writeEndObject();
            }

            generator.writeEndArray();

            generator.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
