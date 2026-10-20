package com.study_tracker.ayush;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

public class PinHashGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        System.out.print("Enter the 4-digit student PIN: ");
        String studentPin = scanner.nextLine();

        System.out.print("Enter the 4-digit shared admin PIN: ");
        String adminPin = scanner.nextLine();

        if (!studentPin.matches("\\d{4}") || !adminPin.matches("\\d{4}")) {
            System.out.println("Error: Both PINs must contain exactly 4 digits.");
            return;
        }

        System.out.println("\nStudent PIN hash:");
        System.out.println(encoder.encode(studentPin));

        System.out.println("\nAdmin PIN hash:");
        System.out.println(encoder.encode(adminPin));

        scanner.close();
    }
}