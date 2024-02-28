package idv.daniel.blogging.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import idv.daniel.blogging.pojo.Category;
import idv.daniel.blogging.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Override
	public void add(Category category) {
		category.setCreateTime(new Timestamp(System.currentTimeMillis()));
		category.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		repository.save(category);

	}

	@Override
	public List<Category> listAll(Integer userId) {

		return repository.findByUserId(userId);

	}

	@Override
	public Category findById(Integer id) {

		Optional<Category> opt = repository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			return null;

		}

	}

	@Override
	public void update(Category category) {
		repository.updateCategory(category.getId(), category.getCategoryName(), category.getCategoryAlias(),
				new Timestamp(System.currentTimeMillis()));

	}

	@Override
	public void delete(Integer id) {
		repository.deleteById(id);

	}

}
