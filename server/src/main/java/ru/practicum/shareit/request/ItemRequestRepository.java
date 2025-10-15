package ru.practicum.shareit.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    List<ItemRequest> findAllByOrderByCreatedDesc();

    @Query(value = "SELECT * FROM requests WHERE user_id = :userId ORDER BY created DESC", nativeQuery = true)
    List<ItemRequest> findByUserId(@Param("userId") Long userId);

    Page<ItemRequest> findByRequestorIdNotOrderByCreatedDesc(Long requestorId, Pageable pageable);

}
