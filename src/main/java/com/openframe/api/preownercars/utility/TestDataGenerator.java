package com.openframe.api.preownercars.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.UUID;

import com.github.javafaker.Faker;

public class TestDataGenerator {

	private static final String DB_URL = "jdbc:postgresql://localhost:5432/Openframe";
	private static final String USER = "postgres";
	private static final String PASSWORD = "password";

	// Image URL
	private static final String IMAGE_URL = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fstock.adobe.com%2Fsearch%3Fk%3Dtoy%2Bcar&psig=AOvVaw17BuZMrlhFQ3gnWUmPoPml&ust=1717782940074000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPj7nKbGx4YDFQAAAAAdAAAAABAJ";

	private static final String[] BRANDS = { "Toyota", "Honda", "Volvo", "Citroën", "Renault", "Peugeot", "BMW", "SEAT",
			"FIAT", "Dacia", "Skoda", "Volkswagen" };

	public static void main(String[] args) {
		try {

			Class.forName("org.postgresql.Driver");

			Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			deleteAllRecords(connection);
			insertRandomUsers(connection);
			insertRandomListings(connection);

			connection.close();

			System.out.println("Successfully inserted data into the database.");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private static void insertRandomUsers(Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection
				.prepareStatement("INSERT INTO users ( email, first_name, last_name) VALUES (?, ?, ?)");

		Faker faker = new Faker();

		for (int i = 0; i <= 200; i++) {

			String email = faker.internet().emailAddress();
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();

			// Set parameters

			preparedStatement.setString(1, email);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, lastName);

			// Execute the insert query
			preparedStatement.executeUpdate();
		}

		// Close statement
		preparedStatement.close();
	}

	private static void insertRandomListings(Connection connection) throws SQLException {
		
		PreparedStatement preparedStatement = connection.prepareStatement(
				"INSERT INTO car_list (id, brand, year, name, price, kilometers_driven, image_url, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

		Faker faker = new Faker();
		Random random = new Random();

		// Image URL
		String imageUrl = "https://www.google.com/imgres?q=car&imgurl=https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2Fthumb%2Fa%2Fa4%2F2019_Toyota_Corolla_Icon_Tech_VVT-i_Hybrid_1.8.jpg%2F1200px-2019_Toyota_Corolla_Icon_Tech_VVT-i_Hybrid_1.8.jpg&imgrefurl=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FCar&docid=mbNbG1lcjhkU7M&tbnid=05RJv03-3XULoM&vet=12ahUKEwjFxITAy8eGAxW4BtsEHRXFOtEQM3oECFwQAA..i&w=1200&h=632&hcb=2&ved=2ahUKEwjFxITAy8eGAxW4BtsEHRXFOtEQM3oECFwQAA";

		// Insert 200 listings
		for (int i = 0; i <= 230; i++) {
			UUID listingId = UUID.randomUUID();
			String brand = BRANDS[random.nextInt(BRANDS.length)];
			int year = faker.number().numberBetween(2000, 2025);
			String name = faker.name().fullName();
			double price = faker.number().randomDouble(2, 1000, 50000);
			int kilometersDriven = faker.number().numberBetween(0, 300000);
			int userId = faker.number().numberBetween(1, 200);
			preparedStatement.setObject(1, listingId);
			preparedStatement.setString(2, brand);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, name);
			preparedStatement.setDouble(5, price);
			preparedStatement.setInt(6, kilometersDriven);
			preparedStatement.setString(7, imageUrl);
			preparedStatement.setInt(8, userId);
			preparedStatement.executeUpdate();
		}

		// Close statement
		preparedStatement.close();
	}
	
	private static void deleteAllRecords(Connection connection) throws SQLException {
        String deleteQueryCarList = "DELETE FROM car_list" ;
        String deleteQueryUsers = "DELETE FROM users" ;
        Statement statement = connection.createStatement();

        statement.executeUpdate(deleteQueryCarList);
        statement.executeUpdate(deleteQueryUsers);

        
        statement.close();
    }

}
