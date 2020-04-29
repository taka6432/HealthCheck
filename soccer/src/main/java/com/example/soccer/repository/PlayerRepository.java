package com.example.soccer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.soccer.domain.Player;

@Repository // 以下のクラスがRepositoryであることを宣言
public interface PlayerRepository extends JpaRepository<Player, Long> { // jpaを継承したinterfaceを作成することでjpaが利用できる

}