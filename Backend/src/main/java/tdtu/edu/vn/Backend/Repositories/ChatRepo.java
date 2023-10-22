package tdtu.edu.vn.Backend.Repositories;

import org.springframework.data.repository.CrudRepository;

import tdtu.edu.vn.Backend.Models.ChatModel;
import tdtu.edu.vn.Backend.Models.UsersModel;

public interface ChatRepo extends CrudRepository<ChatModel, Long> {
    ChatModel findByUsers(UsersModel usersModel);
}
