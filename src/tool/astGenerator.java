package tool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class astGenerator {
    public static void main(String[] args) throws IOException {
        String outputDir;
        if (args.length != 1) {
            outputDir = "C:/Users/GR/Desktop/Projects/ProgrammingLanguages/LOX/jlox/jlox/src/jlox";
        } else {
            outputDir = args[0];
        }
        defineAst(
                outputDir,
                "Expr",
                Arrays.asList(
                    "Binary : Expr left, Token operator, Expr right",
                    "Grouping : Expr expression",
                    "Literal : Object value",
                    "Unary : Token operator, Expr right"
                )
        );
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter pw = new PrintWriter(path, "UTF-8");

        pw.println("package jlox;\n");
        pw.println("import java.util.List;\n");
        pw.println("abstract class " + baseName + " {");

        for (String type : types) {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(pw, baseName, className, fields);
            pw.println();
        }

        pw.println("}");
        pw.close();
    }

    private static void defineType(PrintWriter pw, String baseName, String className, String fieldStr) {
        pw.println("    static class " + className + " extends " + baseName + " {");

        String[] fields = fieldStr.split(",");

        //Fields
        for (String field : fields) {
            pw.println("        final " + field.trim() + ";");
        }

        //Constructor
        pw.println("\n        " + className + "(" + fieldStr + ") {");
        for (String field : fields) {
            String name = field.trim().split(" ")[1];
            pw.println("            this." + name + " = " + name + ";");
        }
        pw.println("        }");

        pw.println("    }");
    }
}
