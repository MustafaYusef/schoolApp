package com.smart.resources.schools_app.core.appDatabase

import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalAnswer
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalQuestion
import com.smart.resources.schools_app.features.onlineExam.data.local.model.UserOnlineExamCrossRef

object SqlUtils {
    const val CREATE_ONLINE_EXAMS_TABLE= "CREATE TABLE IF NOT EXISTS `${LocalOnlineExam.TABLE_NAME}` (`onlineExamId` TEXT NOT NULL, `subjectName` TEXT NOT NULL, `date` INTEGER NOT NULL, `examDuration` INTEGER NOT NULL, `numberOfRequiredQuestions` INTEGER NOT NULL, `numberOfQuestions` INTEGER NOT NULL, `examStatus` INTEGER NOT NULL, `examKey` TEXT NOT NULL, PRIMARY KEY(`onlineExamId`))"
    const val CREATE_USER_ONLINE_EXAM_CROSS_REF_TABLE= "CREATE TABLE IF NOT EXISTS `${UserOnlineExamCrossRef.TABLE_NAME}` (`uid` TEXT NOT NULL, `onlineExamId` TEXT NOT NULL, PRIMARY KEY(`uid`, `onlineExamId`), FOREIGN KEY(`uid`) REFERENCES `UserAccount`(`uid`) ON UPDATE CASCADE ON DELETE CASCADE , FOREIGN KEY(`onlineExamId`) REFERENCES `OnlineExams`(`onlineExamId`) ON UPDATE CASCADE ON DELETE CASCADE )"
    const val CREATE_QUESTIONS_TABLE= "CREATE TABLE IF NOT EXISTS `${LocalQuestion.TABLE_NAME}` (`questionId` TEXT NOT NULL, `onlineExamId` TEXT NOT NULL, `questionText` TEXT NOT NULL, `questionType` INTEGER NOT NULL, `options` TEXT, PRIMARY KEY(`questionId`), FOREIGN KEY(`onlineExamId`) REFERENCES `OnlineExams`(`onlineExamId`) ON UPDATE CASCADE ON DELETE CASCADE )"
    const val CREATE_ANSWERS_TABLE= "CREATE TABLE IF NOT EXISTS `${LocalAnswer.TABLE_NAME}` (`userId` TEXT NOT NULL, `questionId` TEXT NOT NULL, `answerNormal` TEXT NOT NULL, `answerMultiChoice` INTEGER, `answerCorrectness` INTEGER, `correctAnswer` TEXT NOT NULL, PRIMARY KEY(`userId`, `questionId`), FOREIGN KEY(`userId`) REFERENCES `UserAccount`(`uid`) ON UPDATE CASCADE ON DELETE CASCADE , FOREIGN KEY(`questionId`) REFERENCES `Questions`(`questionId`) ON UPDATE CASCADE ON DELETE CASCADE )"
}