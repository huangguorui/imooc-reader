package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.reader.entity.Book;
import com.imooc.reader.mapper.BookMapper;
import com.imooc.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;

    @Override
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer rows) {
        Page<Book> p = new Page<Book>(page, rows);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        if (categoryId != null && categoryId != -1) {
            queryWrapper.eq("category_id", categoryId);
        }
        if (order != null) {
            if (order.equals("quantity")) {
                queryWrapper.orderByDesc("evaluation_quantity");
            } else if (order.equals("score")) {
                queryWrapper.orderByDesc("evaluation_score");
            }
        }
        IPage<Book> pageObject = bookMapper.selectPage(p, queryWrapper);
        return pageObject;
    }


    public Book selectById(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        return book;
    }

    /**
     * 根據圖書編號查詢圖書對象
     */
    @Transactional
    public void updateEvaluation() {
        bookMapper.updateEvaluation();
    }

    /**
     * 創建新的圖書
     *
     * @param book
     */
    @Transactional
    public Book createBook(Book book) {
        bookMapper.insert(book);
        return book;
    }
}
