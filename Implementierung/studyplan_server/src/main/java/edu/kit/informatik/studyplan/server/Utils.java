package edu.kit.informatik.studyplan.server;

import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;
import edu.kit.informatik.studyplan.server.model.userdata.dao.PlanDao;
import edu.kit.informatik.studyplan.server.model.userdata.dao.PlanDaoFactory;
import edu.kit.informatik.studyplan.server.model.userdata.dao.UserDao;
import edu.kit.informatik.studyplan.server.model.userdata.dao.UserDaoFactory;

public class Utils {
    public static <T> boolean hasDuplicates(List<T> list) {
        return new HashSet<T>(list).size() == list.size();
    }

    private static <T> Function<T, Void> consumerToVoidFunction(Consumer<T> consumer) {
        return t -> { consumer.accept(t); return null; };
    }

    public static <T> T withModuleDao(Function<ModuleDao, T> code) {
        ModuleDao dao = ModuleDaoFactory.getModuleDao();
        T result;
        result = code.apply(dao);
        return result;
    }

    public static void useModuleDao(Consumer<ModuleDao> code) {
        withModuleDao(consumerToVoidFunction(code));
    }

    public static <T> T withPlanDao(Function<PlanDao, T> code) {
        PlanDao dao = PlanDaoFactory.getPlanDao();
        T result;
        result = code.apply(dao);
        return result;
    }

    public static void usePlanDao(Consumer<PlanDao> code) {
        withPlanDao(consumerToVoidFunction(code));
    }

    public static <T> T withUserDao(Function<UserDao, T> code) {
        UserDao dao = UserDaoFactory.getUserDao();
        T result;
        result = code.apply(dao);
        return result;
    }

    public static void useUserDao(Consumer<UserDao> code) {
        withUserDao(consumerToVoidFunction(code));
    }
}
