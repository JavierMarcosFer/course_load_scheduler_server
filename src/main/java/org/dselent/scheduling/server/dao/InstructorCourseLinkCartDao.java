package org.dselent.scheduling.server.dao;

import java.util.List;

import org.dselent.scheduling.server.model.InstructorCourseLinkCart;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorCourseLinkCartDao extends Dao<InstructorCourseLinkCart>{
	public int updateInstructorCourseLinkCart(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList);
}
