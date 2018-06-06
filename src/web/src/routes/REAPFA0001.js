import React from 'react'
import { Row } from 'antd'
import FunctionList from '../components/REAPFA0001/ConfigList'
import styles from './REAPFA0001.less'

export default ({
  page, dispatch, effects, showCreateModal,
}) => {
  return (
    <div className={styles.configContainer}>
      <Row>
        <FunctionList page={page} dispatch={dispatch} showCreateModal={showCreateModal} loading={effects['REAPFA0001/query']} />
      </Row>
    </div>
  )
}
