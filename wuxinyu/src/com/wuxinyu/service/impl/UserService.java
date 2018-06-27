package com.wuxinyu.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wuxinyu.entity.User;
import com.wuxinyu.repository.MySpecification;
import com.wuxinyu.repository.MySpecification.Cnd;
import com.wuxinyu.repository.UserRepository;
import com.wuxinyu.service.IUserService;

@Transactional
@Service
public class UserService implements IUserService{
	private static final Logger LOGGER = LogManager.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User save(User user){
		return userRepository.save(user);
	}
	
	@Override
	public void delete(User user){
		userRepository.delete(user);
	}

	@Override
	public void delete(Integer id) {
		userRepository.delete(id);		
	}

	@Override
	public void update(User user) {
		userRepository.saveAndFlush(user);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(Integer id) {
		return userRepository.findOne(id);
	}

	@Override
	public User getUser(String account) {
		return userRepository.findByAccountOrTel(account, account);
	}

	@Override
	public User findByAccount(String account) {
		return userRepository.findByAccountOrTel(account,account);
	}

	@Override
	public User findByIdOrAccountOrTel(Integer id, String account, String tel) {
		return userRepository.findByIdOrAccountOrTel(id,account,tel);
	}
	
	@Override
	public boolean isExist(Integer id){
		return userRepository.exists(id);
	}
	
	/**
	 * 分页查询
	 * @param page 第几页，从0开始，默认为第0页
	 * @param size 每一页的大小，默认为20
	 * @return
	 */
	@Override
	public Page<User> findAll(Integer page,Integer size) {
	    Sort sort = new Sort(Direction.ASC, "id"); //排序
	    Pageable pageable = new PageRequest(page, size, sort);
	    return userRepository.findAll(pageable);
	}
	
	/**
	 * 带条件分页查询
	 * @param map
	 * @return
	 */
	@Override
	public Page<User> findAll(final Map<String, Object> map){
		Integer page = (Integer)map.get("page");
		Integer size = (Integer)map.get("size");
		Sort sort = new Sort(Direction.DESC, "id"); //排序
	    Pageable pageable = new PageRequest(page, size, sort);
	    Page<User> userPage = userRepository.findAll(new Specification<User>() {
				@Override
				public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Path<String> nickname = root.get("nickname");
					Path<String> account = root.get("account");
					
					List<Predicate> predicates = new ArrayList<>();
					if (StringUtils.isNotEmpty(map.get("nickname").toString())) {
		                predicates.add(cb.like(nickname, "%" + map.get("nickname").toString() + "%"));
		            }
		            if (StringUtils.isNotEmpty(map.get("account").toString())) {
		                predicates.add(cb.like(account, "%" + map.get("account").toString() + "%"));
		            }
					query.where(predicates.toArray(new Predicate[0]));
					return null;
				}
		}, pageable);
		return userPage;
	}
	
	/**
	 * 带条件分页查询
	 * @param page
	 * @param rows
	 * @param nickname
	 * @param account
	 * @param telephone
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<User> findAll(int page, int rows, String nickname, String account, String telephone) {
	    PageRequest pr = new PageRequest(page - 1, rows); //条件
	    //使用了自定义的复杂查询，这就比原生的Specification的语法使用流畅多了
	    return userRepository.findAll(new MySpecification<User>().and(
	            Cnd.like("userName", nickname),
	            Cnd.like("account", account),
	            Cnd.eq("telephone", telephone)
	    ).asc("id"), pr);
	}
}
