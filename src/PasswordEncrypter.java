import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JOptionPane;
import java.util.Base64;

public class PasswordEncrypter {

	/**
	 * HashMap data structure that uses key, value pairs to store the usernames and encrypted password.
	 */
	private static HashMap<String, String> codes = new HashMap<String, String>();
	
	public static void main(String[] args) {
		System.out.println("Begin Test");
		System.out.println();
		
		PasswordEncrypter test = new PasswordEncrypter();
		encode();
		decode();
		
	}
	
	/**
	 * Constructor that asks for predetermined set of user names and passwords to input
	 */
	public PasswordEncrypter() {
		//Uses JOptionPane to receive user inputs. 
		String input = JOptionPane.showInputDialog("How many users are there?");
		int numUsers = Integer.parseInt(input);
		User[] users = new User[numUsers];
		
		for(int i = 0; i < numUsers; i++) {
			String username = JOptionPane.showInputDialog("Input Username");
			String password = JOptionPane.showInputDialog("Input Password");
			users[i] = new User(username, password);
			users[i].output();
			this.addUser(users[i]);
		}
	}
	
	/**
	 * Calls all three encode functions on the values for each key in the HashMap storing the data.
	 */
	public static void encode() {
		Iterator<String> keyIterator = codes.keySet().iterator();
		while(keyIterator.hasNext()) {
			
			String key = keyIterator.next();
			String encrypted = codes.get(key);
			System.out.println(key + " Encryption:");
			System.out.println();
			
			encrypted = encode1(encrypted);
			codes.put(key, encrypted);
			
			System.out.println("Username          :  " + key);
			System.out.println("Encrypted Password:  " + codes.get(key));
			System.out.println();
			
			encrypted = encode2(encrypted);
			codes.put(key, encrypted);
			
			System.out.println("Username          :  " + key);
			System.out.println("Encrypted Password:  " + codes.get(key));
			System.out.println();
			
			encrypted = encode3(encrypted);
			codes.put(key, encrypted);
			
			System.out.println("Username          :  " + key);
			System.out.println("Encrypted Password:  " + codes.get(key));
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * Calls all three decode functions on the values for each key in the HashMap storing the data.
	 */
	public static void decode() {
		Iterator<String> keyIterator = codes.keySet().iterator();
		while(keyIterator.hasNext()) {
			
			String key = keyIterator.next();
			String decrypted = codes.get(key);
			
			System.out.println(key + " Decryption:");
			System.out.println();
			
			decrypted= decode3(decrypted);
			
			codes.put(key, decrypted);
			
			System.out.println("Username          :  " + key);
			System.out.println("Decrypted Password:  " + codes.get(key));
			System.out.println();
			
			decrypted= decode2(decrypted);
			
			codes.put(key, decrypted);
			
			System.out.println("Username          :  " + key);
			System.out.println("Decrypted Password:  " + codes.get(key));
			System.out.println();
			

			decrypted = decode1(decrypted);
			codes.put(key, decrypted);
			
			System.out.println("Username          :  " + key);
			System.out.println("Decrypted Password:  " + codes.get(key));
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * Adds an additional user into the HashMap.
	 * @param u - Takes a new User object
	 */
	public void addUser(User u) {
		codes.put(u.getUsername(), u.getPassword());
	}
	
	/**
	 * First round of encoding. Utilizes base 64 encoding by turning the characters 
	 * into their bit representations and then turning those bits into another set of 
	 * characters
	 * @param input - String to encode
	 * @return encoded String
	 */
	public static String encode1(String input) {
		byte[] bytes = input.getBytes();
		
		String encoded = Base64.getEncoder().encodeToString(bytes);
		
		return encoded;
	}
	
	/**
	 * Last round of decoding. Undoes the initial base64 encoding to get the original 
	 * password.
	 * @param encoded - String to decode
	 * @return decoded String
	 */
	public static String decode1(final String encoded) {
		byte[] bytes = Base64.getDecoder().decode(encoded);
		
		String decoded = new String(bytes);
		
		return decoded;
	}
	
	/**
	 * Second round of encoding. This is a simple caesar shift, always by 13. 
	 * References MP3.
	 * @param input - String to encode
	 * @return encoded String
	 */
	public static String encode2(final String input) {
		StringBuilder encoded = new StringBuilder();

		final int shift = 13;
		final int maxChar = 126;
		final int minChar = 32;
		final int interval = 95;
		int valChar;

		boolean isInRange = false;

		char[] encrypted = new char[input.length()];
		for(int i = 0; i < encrypted.length; i++) {
			encrypted[i] = input.charAt(i);
		}

		for (int i = 0; i < input.length(); i += 1) {
			valChar = (int) encrypted[i];
			if (valChar >= minChar && valChar <= maxChar) {
				int jump = 0;
				isInRange = false;
				do {
					if (valChar + shift + (jump * interval) < minChar) {
						jump += 1;
					} else if (valChar + shift + (jump * interval) > maxChar) {
						jump -= 1;
					} else {
						isInRange = true;
					}
				} while (!isInRange);

				encrypted[i] = (char) (valChar + shift + (jump * interval));

			} else {
				return null;
			}

		}

		for(int i =0; i < encrypted.length; i++) {
			encoded.append(encrypted[i]);
		}
		
		return encoded.toString();
	}
	
	/**
	 * Second round of decoding. This undoes the caesar shift. References MP3.
	 * @param encoded - String to decode
	 * @return decoded String
	 */
	public static String decode2(String encoded) {
		StringBuilder decoded = new StringBuilder();
		
		final int shift = 13;
		final int maxChar = 126;
        final int minChar = 32;
        final int interval = 95;
        int valChar;

        boolean isInRange = false;
        
        char[] decrypted = new char[encoded.length()];
        for(int i = 0; i < decrypted.length; i++) {
        	decrypted[i] = encoded.charAt(i);
        }

        for (int i = 0; i < decrypted.length; i += 1) {
            valChar = (int) decrypted[i];
            if (valChar >= minChar && valChar <= maxChar) {
                int jump = 0;
                   isInRange = false;
                do {

                    if (valChar - shift + (jump * interval) < minChar) {
                        jump += 1;
                    } else if (valChar - shift + (jump * interval) > maxChar) {
                        jump -= 1;
                    } else {
                        isInRange = true;
                    }
                } while (!isInRange);
                decrypted[i] = (char) (valChar - shift + jump * interval);

            } else {
                return null;
            }
        }
        
        for (int i = 0; i < decrypted.length; i++) {
        	decoded.append(decrypted[i]);
        }

        return decoded.toString();
		
	}
	
	
	/**
	 * This is the third round of encoding. All this does is chop the string into 
	 * chars and then find their int value. The int value is then converted into base16 
	 * and a string of hexidecimal numbers is the output. 
	 * @param input - String to encode
	 * @return the encoded String
	 */
	public static String encode3(String input) {
		StringBuilder encoded = new StringBuilder();
		
		for(int i = 0; i < input.length(); i++) {
			encoded.append(Integer.toHexString((int) input.charAt(i)));
		}
		
		return encoded.toString();
	}
	
	/**
	 * This is the first round of decoding. This parses the String of base16 numbers,
	 * taking each 2 consecutive characters and converting them into base 10 and then 
	 * into their ascii character. 
	 * @param encoded - String to decode
	 * @return the decoded String
	 */
	public static String decode3(String encoded) {
		StringBuilder decoded = new StringBuilder();
		
		for(int i = 0; i < encoded.length() - 1; i += 2) {
			int hexi = Integer.parseInt(encoded.substring(i,i + 2),16);
			decoded.append((char)hexi);
		}
	
		return decoded.toString();
	}

	/**
	 * @return Returns the HashMap object.
	 */
	public HashMap getCodes() {
		return codes;
	}
	

}
