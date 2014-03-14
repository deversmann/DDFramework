package com.eversmann.framework.dd.template.data;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eversmann.framework.dd.data.GenericDaoImpl;
import com.eversmann.framework.dd.template.domain.Hello;


/**
 * @author dje
 *
 */
@Repository("helloDao")
@Transactional
public class HelloDaoImpl extends GenericDaoImpl<Hello, Long> implements HelloDao {

}
