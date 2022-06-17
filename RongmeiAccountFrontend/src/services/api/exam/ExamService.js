import {httpCommon} from '../HttpService';

export class ExamServiceImpl {
    async getAllExercise() {
        return await httpCommon.get(
            '/exam/exercise/all'
        )
    }

    async updateExercise(id, tag, question, answer, selections) {
        return await httpCommon.post(
            '/exam/exercise',
            {
                id, tag, question, answer, selections
            }
        )
    }

    async deleteExercise(id) {
        return await httpCommon.delete(
            `/exam/exercise/${id}`
        )
    }

    async approveThinkTankExercise(exerciseId, isApprove) {
        return await httpCommon.get(
            `/exam/think_tank/exercise/approval?exerciseId=${exerciseId}&isApprove=${isApprove}`
        )
    }

    async getAllThinkTankExercises() {
        return await httpCommon.get(
            `/exam/think_tank/exercise`
        )
    }
}
