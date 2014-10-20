package org.jboss.ddoyle.brms.workshop.compiler;

import java.io.IOException;
import java.io.InputStream;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;

public class MySpreadsheetCompiler {

	public static void main(String[] args) {
		SpreadsheetCompiler compiler = new SpreadsheetCompiler();
		InputStream spreadSheet = null;
		try {
			spreadSheet = MySpreadsheetCompiler.class.getClassLoader()
					.getResourceAsStream("person-dt-2.xls");
			// compiler.compile(arg0, arg1)
			String rules = compiler.compile(spreadSheet, InputType.XLS);

			System.out.println(rules);
		} finally {
			if (spreadSheet != null) {
				try {
					spreadSheet.close();
				} catch (IOException e) {
					throw new RuntimeException(
							"Error closing spreadsheet inputstream.");
				}
			}
		}

	}

}
