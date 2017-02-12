package edu.kit.informatik.studyplan.server;

import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDaoFactory;
import edu.kit.informatik.studyplan.server.model.userdata.dao.PlanDao;
import edu.kit.informatik.studyplan.server.model.userdata.dao.PlanDaoFactory;
import edu.kit.informatik.studyplan.server.model.userdata.dao.UserDao;
import edu.kit.informatik.studyplan.server.model.userdata.dao.UserDaoFactory;

import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Utility class for several helper methods.
 */
public class Utils {
    /**
     *
     * @param list the list to check
     * @param <T> the list's type parameter
     * @return if the list has duplicates
     */
    public static <T> boolean hasDuplicates(List<T> list) {
        return new HashSet<T>(list).size() == list.size();
    }

    /**
     *
     * @param consumer the consumer to convert
     * @param <T> the consumer's parameter type
     * @return the consumer converted to a function returning Void.
     */
    private static <T> Function<T, Void> consumerToVoidFunction(Consumer<T> consumer) {
        return t -> { consumer.accept(t); return null; };
    }

    /**
     * Provides a ModuleDao to run code with. Can be used for ensuring correct cleaning up.
     * @param code the code to run.
     * @param <T> the return type of the code
     * @return the code's result
     */
    public static <T> T withModuleDao(Function<ModuleDao, T> code) {
        ModuleDao dao = ModuleDaoFactory.getModuleDao();
        return code.apply(dao);
    }

    /**
     * Provides a ModuleDao to run code with. Can be used for ensuring correct cleaning up.
     * @param code the code to run.
     */
    public static void useModuleDao(Consumer<ModuleDao> code) {
        withModuleDao(consumerToVoidFunction(code));
    }

    /**
     * Provides a PlanDao to run code with. Can be used for ensuring correct cleaning up.
     * @param code the code to run.
     * @param <T> the return type of the code
     * @return the code's result
     */
    public static <T> T withPlanDao(Function<PlanDao, T> code) {
        PlanDao dao = PlanDaoFactory.getPlanDao();
        return code.apply(dao);
    }

    /**
     * Provides a Plan to run code with. Can be used for ensuring correct cleaning up.
     * @param code the code to run.
     */
    public static void usePlanDao(Consumer<PlanDao> code) {
        withPlanDao(consumerToVoidFunction(code));
    }

    /**
     * Provides a UserDao to run code with. Can be used for ensuring correct cleaning up.
     * @param code the code to run.
     * @param <T> the return type of the code
     * @return the code's result
     */
    public static <T> T withUserDao(Function<UserDao, T> code) {
        UserDao dao = UserDaoFactory.getUserDao();
        return code.apply(dao);
    }

    /**
     * Provides a UserDao to run code with. Can be used for ensuring correct cleaning up.
     * @param code the code to run.
     */
    public static void useUserDao(Consumer<UserDao> code) {
        withUserDao(consumerToVoidFunction(code));
    }
}
