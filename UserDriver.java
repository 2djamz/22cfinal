package socialNetworkApplication;

import java.util.Iterator;
import java.util.Scanner;

//1. create example user profiles

public class UserDriver<T> {
	private static UndirectedGraph<String> network = new UndirectedGraph<>();
	private static final String u1 = "luna";
	private static final String u2 = "david";
	private static final String u3 = "fernando";
	private static final String u4 = "francisco";
	private static final String u5 = "bardia";
	private static final String u6 = "elizabeth";
	private static final String u7 = "ashton";

	public static void main(String[] args) {

		String userInput = null;
		boolean networkActive = true;
		String userPassword = null;
		existingUsers();

		while (networkActive) {
			System.out.println("\n\nWelcome to SocialNetwork!");
			System.out.println("Do you already have an account? Enter 'Yes' or 'No'.");
			userInput = getInput();

			if (userInput.equals("yes")) {
				System.out.println("\nEnter your name below:");
				userInput = getInput();
				System.out.println("\nEnter your password below:");
				userPassword = getInput();
				} 
			else if (userInput.equals("no")) {
				System.out.println("\nWould you like to create a new account?");
				System.out.println("Please enter 'Yes' or 'No'.");
				userInput = getInput();

				if (userInput.equals("yes")) {
					System.out.println("Please enter your name below:");
					networkActive = setNewUser(getInput());

				} else if (userInput.equals("no")) {
					System.out.println("\nNo worries! Would you like to go back to the main menu?");
					System.out.println("Please enter 'Yes' or 'No'.");

					if (getInput().equals("no")) {
						networkActive = false;
					}
				}
			} else {
				System.out.println("Invalid input.");
				System.out.println("Would you like to start again? Please enter 'Yes' or 'No'.");
				userInput = getInput();

				if (userInput.equals("no")) {
					networkActive = false;
				}
			}
		}
		System.out.println("\n\nGoodbye!");
	}

	public static boolean insideSystem(String userName) {
		String userInput = null;
		boolean networkActive = true;

		System.out.println("\n\nWelcome, " + userName + ", to SocialNetwork");
		System.out.println("There are a total of " + network.getNumberOfVertices() + " people in the network");

		while (networkActive) {
			System.out.println("\nWhat would you like to do? Please enter a number.");

			System.out.println("1. Update username\n2. Update profile picture\n3. Update status");
			System.out.println("4. See list of your friends\n5. Add new friends");
			System.out.println("6. See friend's friends\n7. Search for other profiles");
			System.out.println("8. Show your profile\n9. Log Out\n10.Show all members and their friends");
			userInput = getInput();

			if (userInput.equals("1")) {
				System.out.println("Please enter your new name:");
				userInput = getInput();
				network.renameKey(userName, userInput);
				userName = userInput;
				getAUser(userName).setName(userName);

				System.out.println("Your new name is: " + getAUser(userName).getName());

			} else if (userInput.equals("2")) { // <-------- WORKS-------------------------------------------
				System.out.println("Please enter your new image:");
				getAUser(userName).setImg(getInput());

				System.out.println("Your new image is: " + getAUser(userName).getImg());

			} else if (userInput.equals("3")) { 
				System.out.println("Please enter your new status:");
				getAUser(userName).setStatus(getInput());

				System.out.println("Your new status is: " + getAUser(userName).getStatus());

			} else if (userInput.equals("4")) { 
				network.displayFriends(userName);

			} else if (userInput.equals("5")) { 
				network.displayEdges();
				System.out.println("\nType a name of a person you want to add as a friend:");
				userInput = getInput();

				if (checkUsername(userInput)) {
					System.out.println("\nCongrats! Friend added successfully.");
					network.addEdge(userName, userInput);
					network.displayFriends(userName);

				} else {
					System.out.println("\nUSER NOT FOUND. Please try again.");
				}
			} else if (userInput.equals("6")) {
				network.displayFriendsOfFriends(userName);

			} else if (userInput.equals("7")) { 
				System.out.println("Enter the name of the person you're searching for:");
				userInput = getInput();

				if (checkUsername(userInput)) {

					System.out.println("\nUser found!");
					UserInterface<String> searchedUser = getAUser(userInput);

					System.out.println("\n\nName: " + searchedUser.getName());
					System.out.println("Image URL: " + searchedUser.getImg());
					System.out.println("Status: " + searchedUser.getStatus());
					network.displayFriends(searchedUser.getName());

					System.out.println("\nAdd as a friend?");
					userInput = getInput();
					if (userInput.equals("yes")) {
						network.addEdge(userName, searchedUser.getName());
						network.displayFriends(userName);

					} else if (userInput.equals("no")) {
						System.out.println("\nGoing back to menu");
					}
				} else {
					System.out.println("USER NOT FOUND. Please try again");
				}
			} else if (userInput.equals("8")) { 
				System.out.println("Printing all your profile information:");

				System.out.println("Name: " + getAUser(userName).getName());
				System.out.println("Image Link: " + getAUser(userName).getImg());
				System.out.println("Status: " + getAUser(userName).getStatus());
				network.displayFriends(userName);

			} else if (userInput.equals("9")) { 
				networkActive = false;
				
			} else if (userInput.equals("10")) { 
				System.out.println("\nAll members and their friends:");
				network.displayEdges();

			} else {
				System.out.println("\nInvalid input!");
				System.out.println("Would you like to try again? Enter 'Yes' or 'No'.");
				userInput = getInput();

				if (userInput.equals("no")) {
					networkActive = false;
				}
			}
		}
		return networkActive;
	}

	public static UserInterface<String> getAUser(String user) {
		UserInterface<String> aUser = network.getUser(user);
		return aUser;
	}

	public static boolean checkUsername(String userName) {
		return network.hasVertex(userName);
	}

	public static boolean checkPassword(String userName, String password) {
		UserInterface<String> user = getAUser(userName); 
		if (user.getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	public static boolean setNewUser(String userName) {
		boolean networkActive = true;
		String userInput = null;

		while (checkUsername(userName)) {
			System.out.println("Account already exists. Please enter another username");
			System.out.println("\nPlease enter your name below:");
			userName = getInput();
		}

		System.out.println("\nHello " + userName + "!");
		System.out.println("Please enter your new password below:");
		userInput = getInput();
		System.out.println("\nSetting up your account now....");

		network.addVertex(userName);
		getAUser(userName).setName(userName);
		getAUser(userName).setPassword(userInput);

		System.out.println("Your name is: " + getAUser(userName).getName());
		System.out.println("Your password is: " + getAUser(userName).getPassword());

		System.out.println("\nWould you like to complete account setup?");
		System.out.println("Please enter 'Yes' or 'No'.");
		userInput = getInput();

		if (userInput.equals("yes")) {
			System.out.println("Enter your new status below:");
			getAUser(userName).setStatus(getInput());

			System.out.println("\nYour new status is: " + getAUser(userName).getStatus() + "\n");

			System.out.println("Enter your new image link below:");
			getAUser(userName).setImg(getInput());

			System.out.println("\nYour new image is: " + getAUser(userName).getImg() + "\n");
		}
		System.out.println("\nDo you want to go to the home page?");
		userInput = getInput();

		if (userInput.equals("yes")) {
			insideSystem(userName);
		} else if (userInput.equals("no")) {
			System.out.println("See you later!");
			networkActive = false;
		}

		return networkActive;
	}

	@SuppressWarnings("resource")
	private static String getInput() {
		Scanner input;
		String inString = "";

		input = new Scanner(System.in);
		inString = input.nextLine().trim().toLowerCase();

		return inString;
	}

	private static void existingUsers() {
		network.addVertex(u1);
		network.addVertex(u2);
		network.addVertex(u3);
		network.addVertex(u4);
		network.addVertex(u5);
		network.addVertex(u6);
		network.addVertex(u7);

		getAUser(u1).setName(u1);
		getAUser(u2).setName(u2);
		getAUser(u3).setName(u3);
		getAUser(u4).setName(u4);
		getAUser(u5).setName(u5);
		getAUser(u6).setName(u6);
		getAUser(u7).setName(u7);

		network.addEdge(u1, u2);
		network.addEdge(u1, u3);
		network.addEdge(u1, u5);
		network.addEdge(u1, u6);
		network.addEdge(u1, u7);
		network.addEdge(u2, u3);
		network.addEdge(u4, u5);
		network.addEdge(u5, u6);
		network.addEdge(u5, u7);
		network.addEdge(u6, u7);

		getAUser(u1).setPassword("123");
		getAUser(u2).setPassword("1234");
		getAUser(u3).setPassword("12345");
		getAUser(u4).setPassword("123456");
		getAUser(u5).setPassword("1234567");
		getAUser(u6).setPassword("12345678");
		getAUser(u7).setPassword("123456789");

		getAUser(u1).setImg("catpicture.jpg");
		getAUser(u2).setImg("myface.png");
		getAUser(u3).setImg("lizard.png");
		getAUser(u4).setImg("fox.png");
		getAUser(u5).setImg("cow.png");
		getAUser(u6).setImg("selfie.png");
		getAUser(u7).setImg("photo.png");


		getAUser(u1).setStatus("New account!");
		getAUser(u2).setStatus("hey");
		getAUser(u3).setStatus("watching a movie");
		getAUser(u4).setStatus("new profile picture!");
		getAUser(u5).setStatus("Message me!!");
		getAUser(u6).setStatus(":)");
		getAUser(u7).setStatus("Hi");
	}
}


//RUN
/*


Welcome to SocialNetwork!
Do you already have an account? Enter 'Yes' or 'No'.
no

Would you like to create a new account?
Please enter 'Yes' or 'No'.
yes
Please enter your name below:
lemon

Hello lemon!
Please enter your new password below:
ss

Setting up your account now....
Your name is: lemon
Your password is: ss

Would you like to complete account setup?
Please enter 'Yes' or 'No'.
yes
Enter your new status below:
jss

Your new status is: jss

Enter your new image link below:
ss

Your new image is: ss


Do you want to go to the home page?
yes


Welcome, lemon, to SocialNetwork
There are a total of 8 people in the network

What would you like to do? Please enter a number.
1. Update username
2. Update profile picture
3. Update status
4. See list of your friends
5. Add new friends
6. See common friends
7. Search for other profiles
8. Show your profile
9. Log Out
10.Show all members and their friends
1
Please enter your new name:
ddhd
Your new name is: ddhd

What would you like to do? Please enter a number.
1. Update username
2. Update profile picture
3. Update status
4. See list of your friends
5. Add new friends
6. See common friends
7. Search for other profiles
8. Show your profile
9. Log Out
10.Show all members and their friends
2
Please enter your new image:
dd
Your new image is: dd

What would you like to do? Please enter a number.
1. Update username
2. Update profile picture
3. Update status
4. See list of your friends
5. Add new friends
6. See common friends
7. Search for other profiles
8. Show your profile
9. Log Out
10.Show all members and their friends
3
Please enter your new status:
ss
Your new status is: ss

What would you like to do? Please enter a number.
1. Update username
2. Update profile picture
3. Update status
4. See list of your friends
5. Add new friends
6. See common friends
7. Search for other profiles
8. Show your profile
9. Log Out
10.Show all members and their friends
5

All users and their friends:
ddhd: 
ashton: luna bardia elizabeth 
elizabeth: luna bardia ashton 
bardia: luna francisco elizabeth ashton 
francisco: bardia 
fernando: luna david 
david: luna fernando 
luna: david fernando bardia elizabeth ashton 

Type a name of a person you want to add as a friend:
luna

Congrats! Friend added successfully.
Here's a list of all ddhd's friends:
luna 

What would you like to do? Please enter a number.
1. Update username
2. Update profile picture
3. Update status
4. See list of your friends
5. Add new friends
6. See common friends
7. Search for other profiles
8. Show your profile
9. Log Out
10.Show all members and their friends
6
Here's a list of all ddhd's friends of friends:
luna 

What would you like to do? Please enter a number.
1. Update username
2. Update profile picture
3. Update status
4. See list of your friends
5. Add new friends
6. See common friends
7. Search for other profiles
8. Show your profile
9. Log Out
10.Show all members and their friends
7
Enter the name of the person you're searching for:
david

User found!


Name: david
Image URL: myface.png
Status: hey
Here's a list of all david's friends:
luna fernando 
luna fernando 

Add as a friend?
yes
Here's a list of all ddhd's friends:
luna david 
luna david 

What would you like to do? Please enter a number.
1. Update username
2. Update profile picture
3. Update status
4. See list of your friends
5. Add new friends
6. See common friends
7. Search for other profiles
8. Show your profile
9. Log Out
10.Show all members and their friends
8
Printing all your profile information:
Name: ddhd
Image Link: dd
Status: ss
Here's a list of all ddhd's friends:
luna david 
luna david 

What would you like to do? Please enter a number.
1. Update username
2. Update profile picture
3. Update status
4. See list of your friends
5. Add new friends
6. See common friends
7. Search for other profiles
8. Show your profile
9. Log Out
10.Show all members and their friends



*/