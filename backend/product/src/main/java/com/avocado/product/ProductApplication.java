package com.avocado.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ProductApplication {

	public static void main(String[] args) throws IOException {
		System.setProperty("spring.devtools.restart.enabled", "false"); // restart / launcher 클래스 로더 이슈 없애줌
		SpringApplication.run(ProductApplication.class, args);

//		Click click1 = new Click();
//		click1.setProductId("abc");
//
//		Click click2 = new Click("bcd");
//
//		Click click3 = Click.newBuilder()
//				.setProductId("cde")
//				.build();
//
//		DatumWriter<Click> userDatumWriter = new SpecificDatumWriter<Click>(Click.class);
//		DataFileWriter<Click> dataFileWriter = new DataFileWriter<Click>(userDatumWriter);
//		dataFileWriter.create(click1.getSchema(), new File("clicks.avro"));
//		dataFileWriter.append(click1);
//		dataFileWriter.append(click2);
//		dataFileWriter.append(click3);
//		dataFileWriter.close();
//
//		DatumReader<Click> userDatumReader = new SpecificDatumReader<Click>(Click.class);
//		DataFileReader<Click> dataFileReader = new DataFileReader<Click>(new File("clicks.avro"), userDatumReader);
//		Click click = null;
//		while (dataFileReader.hasNext()) {
//			// Reuse user object by passing it to next(). This saves us from
//			// allocating and garbage collecting many objects for files with
//			// many items.
//			click = dataFileReader.next(click);
//			System.out.println(click);
//		}
	}

}
