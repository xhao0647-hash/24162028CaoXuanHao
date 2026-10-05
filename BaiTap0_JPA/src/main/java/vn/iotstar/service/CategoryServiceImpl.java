package vn.iotstar.service;

import vn.iotstar.dao.CategoryDaoImpl;
import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.entity.Category;

import java.util.List;

public class CategoryServiceImpl implements ICategoryService {
    private final ICategoryDao cateDao = new CategoryDaoImpl();

    @Override
    public void insert(Category category) { cateDao.insert(category); }

    @Override
    public void update(Category category) { cateDao.update(category); }

    @Override
    public void delete(int cateid) throws Exception { cateDao.delete(cateid); }

    @Override
    public Category findById(int cateid) { return cateDao.findById(cateid); }

    @Override
    public List<Category> findAll() { return cateDao.findAll(); }
}
