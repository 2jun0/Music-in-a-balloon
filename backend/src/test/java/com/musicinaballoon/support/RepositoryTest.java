package com.musicinaballoon.support;

import com.musicinaballoon.common.config.JpaAuditingConfig;
import com.musicinaballoon.common.config.QueryDslConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest(showSql = false)
@Import({QueryDslConfig.class, JpaAuditingConfig.class})
public @interface RepositoryTest {
}
