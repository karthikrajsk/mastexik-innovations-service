package com.tech3.erp.util;

import com.tech3.erp.dto.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.function.Function;

public class PaginationHelper {
    private static final long DEFAULT_LIMIT = 20;
    private static final long MIN_LIMIT = 1;
    private static final long MAX_LIMIT = 10000;

    public static <T> PaginatedResponse<T> paginate(List<T> allData, long offset, long limit) {
        limit = validateAndAdjustLimit(limit);
        offset = Math.max(0, offset);

        long total = allData.size();
        int startIndex = (int) Math.min(offset, total);
        int endIndex = (int) Math.min(offset + limit, total);

        List<T> paginatedData = allData.subList(startIndex, endIndex);

        boolean hasNext = endIndex < total;
        boolean hasPrev = offset > 0;

        return new PaginatedResponse<>(total, hasNext, hasPrev, offset, limit, paginatedData);
    }

    public static <T> PaginatedResponse<T> paginate(List<T> allData, Long offset, Long limit) {
        long off = offset != null ? offset : 0;
        long lim = limit != null ? limit : DEFAULT_LIMIT;
        return paginate(allData, off, lim);
    }

    public static long sanitizeLimit(Long limit) {
        return validateAndAdjustLimit(limit == null ? DEFAULT_LIMIT : limit);
    }

    private static long validateAndAdjustLimit(long limit) {
        if (limit <= 0) {
            return DEFAULT_LIMIT;
        }
        return Math.min(limit, MAX_LIMIT);
    }

    public static long getDefaultLimit() {
        return DEFAULT_LIMIT;
    }

    public static Pageable getPageable(int page, int size) {
        return PageRequest.of(page, size);
    }

    public static <T, R> PaginatedResponse<R> toPaginatedResponse(Page<T> page, Function<T, R> mapper) {
        List<R> content = page.getContent().stream()
                .map(mapper)
                .toList();
        return new PaginatedResponse<>(
                page.getTotalElements(),
                page.hasNext(),
                page.hasPrevious(),
                page.getNumber(),
                page.getSize(),
                content
        );
    }
}
