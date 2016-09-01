package com.lanyine.manifold.base.beans;

/**
 * 简单分页信息，不包含分页内容，只包含分页信息
 *
 * @author shadow
 */
public class Pagination extends PageRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 查询的总条数
     */
    private int total;

    /**
     * 最大页码
     */
    private int maxPage;

    public Pagination() {
    }

    public Pagination(int page, int pageSize, int total) {
        super(new PageRequest(page, pageSize));
        setTotal(total);
    }

    protected Pagination(PageRequest page, int total) {
        super(page);
        setTotal(total);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total < 0 ? 0 : total;
        this.setMaxPage((this.total - 1) / getPageSize() + 1);
    }

    public int getMaxPage() {
        return maxPage;
    }

    private void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }
}
