/*
 * Copyright (c) 2026 Sakire Arslan Ay
 *
 * This file was developed for educational purposes as part of CS4233:
 * Object-Oriented Analysis & Design at Worcester Polytechnic Institute.
 *
 * All rights reserved. Redistribution and modification outside the scope
 * of this course are not permitted without prior written permission.
 */
package spreadsheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "spreadsheet")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
