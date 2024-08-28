package main.com.ecommerce.services;

import main.com.ecommerce.models.User;
import main.com.ecommerce.exceptions.UserNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class UserService {
    private List<User> users;

    public UserService(List<User> users) {
        this.users = users;
    }
    
    public List<User> getAllUsers() {
        return users;
    }
    
    public User createUser(User user) {
        users.add(user);
        return user;
    }

    public User getUserById(int id) throws UserNotFoundException {
        Optional<User> user = users
        		.stream().filter(u -> u.getId() == id)
        		.findFirst();
        
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

    public User updateUser(int id, User updatedUser) throws UserNotFoundException {
        User existingUser = getUserById(id);

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());

        return existingUser;
    }

    public void deleteUser(int id) throws UserNotFoundException {
        User user = getUserById(id);
        users.remove(user);
    }
    
    public User getUserByUsername(String username) throws UserNotFoundException {
        Optional<User> user = users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("User not found with username " + username);
        }
    }

    public User authenticate(String username, String password) {
        Optional<User> user = users.stream()
                                   .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                                   .findFirst();
        return user.orElse(null);
    }
    
 // Generate a new user ID
    public int generateUserId() {
        return users.stream()
                    .max(Comparator.comparingInt(User::getId))
                    .map(user -> user.getId() + 1)
                    .orElse(1);
    }
    
//  public User getUserByEmail(String email) throws UserNotFoundException {
//  Optional<User> user = users.stream().filter(u -> u.getEmail().equals(email)).findFirst();
//  if (user.isPresent()) {
//      return user.get();
//  } else {
//      throw new UserNotFoundException("User not found with email " + email);
//  }
//}
//
//public List<User> getUsersByRole(String role) {
//  return users.stream().filter(u -> u.getRole().equalsIgnoreCase(role)).toList();
//}

//public User updateUserRole(int id, String newRole) throws UserNotFoundException {
//  User existingUser = getUserById(id);
//  existingUser.setRole(newRole);
//  return existingUser;
//}
    
//  public void addUser(User user) {
//  users.add(user);
//}
}
