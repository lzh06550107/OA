package cn.itcast.service;

import java.util.List;

import org.jbpm.api.task.Task;

import cn.itcast.domain.Application;
import cn.itcast.domain.ApproveInfo;
import cn.itcast.domain.User;
import cn.itcast.utils.Pair;

public interface IFlowService {

	public void submit(Application app);

	public List<Pair<Application, Task>> findTaskList(User currentUser);

	public void approve(ApproveInfo ai, String taskId);

}
