package de.kasperczyk.rkbudget.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CategoryRepository extends JpaRepository<Category, Long> {
}
