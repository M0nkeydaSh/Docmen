package ru.imsit.diplom.docmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.imsit.diplom.docmen.entity.Comments;

import java.util.UUID;

public interface CommentsRepository extends JpaRepository<Comments, UUID>, JpaSpecificationExecutor<Comments> {
}