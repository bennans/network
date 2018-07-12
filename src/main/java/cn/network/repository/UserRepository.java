package cn.network.repository;

import cn.network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer>{
    List<User> findByEmailAndPwd(String email, String pwd);
}
