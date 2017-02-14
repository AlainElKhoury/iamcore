/**
 * 
 */
package fr.epita.iam.business;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.IdentityJDBCDAO;

/**
 * @author tbrou
 *
 */
public class CreateActivity {
	
	
	public static void create(Scanner scanner){
		System.out.println("Identity Creation");
		System.out.println("Please enter the displayName:");
		String displayName = scanner.nextLine();
		System.out.println("Please enter the email address:");
		String email = scanner.nextLine(); 
		System.out.println("Please enter yout birthdate in this format: yyyy-mm-dd");
		String birthdate = scanner.nextLine();
		Identity identity = new Identity("",displayName, email, birthdate);
		IdentityJDBCDAO identityJDBCDAO = new IdentityJDBCDAO();
		try{
			identityJDBCDAO.write(identity);
			System.out.println("Creation Done");
		} catch(SQLException except){
			System.out.println("Information Wrong");
		}
		
	}
	
	public static void update (Scanner scanner){
		IdentityJDBCDAO identityJDBCDAO = new IdentityJDBCDAO();
		Identity identity_update=null;
		try {
			List<Identity> identity_list = identityJDBCDAO.readAllIdentities();
			for(Identity x : identity_list){
				System.out.println(x.toString());
			}
			System.out.println("Please enter the ID:");
			try {
				int id = scanner.nextInt();
				for(Identity x : identity_list){
					int id_identity = Integer.parseInt(x.getUid());
					if(id==id_identity){
						identity_update=x;
						break;
					}
				}
				System.out.println("MENU");
				System.out.println("Please make your choice");
				System.out.println("a) Edit username"); 
				System.out.println("b) Edit email");
				System.out.println("c) Edit birthdate");
				System.out.println("d) Quit");
				Scanner scan = new Scanner(System.in);
				String choice = scan.nextLine();
				switch (choice) {
				case "a":
					System.out.println("Enter new username");
					String newuser = scan.nextLine();
					identity_update.setDisplayName(newuser);
					break;
				case "b":
					System.out.println("Enter new email");
					String newemail = scan.nextLine();
					identity_update.setEmail(newemail);
					break;
					
				case "c":
					System.out.println("Enter new birthdate");
					String newbirth = scan.nextLine();
					identity_update.setBirthdate(newbirth);
					break;
					
				case "d":
					//Quit
					break;
					
				default:
					System.out.println("Your choice is not recognized");
					break;
				}
				identityJDBCDAO.update(identity_update);
			}catch (InputMismatchException miss){
				System.out.println("Invalid ID");
			}
			
		} catch (SQLException except) {
			// TODO Auto-generated catch block
			except.printStackTrace();
		}
	}
	
	public static void delete(Scanner scanner) throws SQLException {
		System.out.println("Please enter the email:");
		try {
			  String email = scanner.nextLine();
			  IdentityJDBCDAO identityJDBCDAO = new IdentityJDBCDAO();
			  identityJDBCDAO.delete(email);
		
		}catch (SQLException e) {
			// TODO: handle exception
		}

	}	
}
