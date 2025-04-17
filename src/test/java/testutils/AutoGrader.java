package testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.Statement;

public class AutoGrader {

	// Test if the code correctly implements collection operations
	public boolean testCollectionOperations(String filePath) throws IOException {
		System.out.println("Starting testCollectionOperations with file: " + filePath);

		File participantFile = new File(filePath); // Path to participant's file
		if (!participantFile.exists()) {
			System.out.println("File does not exist at path: " + filePath);
			return false;
		}

		FileInputStream fileInputStream = new FileInputStream(participantFile);
		JavaParser javaParser = new JavaParser();
		CompilationUnit cu;
		try {
			cu = javaParser.parse(fileInputStream).getResult()
					.orElseThrow(() -> new IOException("Failed to parse the Java file"));
		} catch (IOException e) {
			System.out.println("Error parsing the file: " + e.getMessage());
			throw e;
		}

		System.out.println("Parsed the Java file successfully.");

		// Flags to check presence and calls of specific methods
		boolean hasMainMethod = false;

		// Check for method declarations
		for (MethodDeclaration method : cu.findAll(MethodDeclaration.class)) {
			String methodName = method.getNameAsString();
			// Check for the presence of the main method
			if (methodName.equals("main")) {
				hasMainMethod = true;
			}
		}

		// Check for method calls in the entire class
		boolean calledAdd = false;
		boolean calledRemove = false;
		boolean calledSize = false;

		for (MethodCallExpr methodCall : cu.findAll(MethodCallExpr.class)) {
			String methodName = methodCall.getNameAsString();
			if (methodName.equals("add")) {
				calledAdd = true;
			} else if (methodName.equals("remove")) {
				calledRemove = true;
			} else if (methodName.equals("size")) {
				calledSize = true;
			}
		}

		// Check if the iterator methods are used anywhere in the class
		boolean loopedThroughList = false;

		// Search through all the statements in the entire class
		for (Statement stmt : cu.findAll(Statement.class)) {
			String statement = stmt.toString();
			if (statement.contains("iterator()") && statement.contains("hasNext()") && statement.contains("next()")) {
				loopedThroughList = true;
				break; // Exit the loop early since we found the loop
			}
		}

		// Log method presence and calls
		System.out.println("Method 'main' is " + (hasMainMethod ? "present" : "NOT present"));
		System.out.println("Method 'add' is " + (calledAdd ? "called" : "NOT called"));
		System.out.println("Method 'remove' is " + (calledRemove ? "called" : "NOT called"));
		System.out.println("Method 'size' is " + (calledSize ? "called" : "NOT called"));
		System.out.println("Looping through List is " + (loopedThroughList ? "implemented" : "NOT implemented"));

		// Final result
		boolean result = hasMainMethod && calledAdd && calledRemove && calledSize && loopedThroughList;

		System.out.println("Test result: " + result);
		return result;
	}
}
