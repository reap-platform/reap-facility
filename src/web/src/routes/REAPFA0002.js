import React from 'react'
import { Row } from 'antd'
import FunctionList from '../components/REAPFA0002/RouteList'
import styles from './REAPFA0002.less'

export default ({
  page, dispatch, effects, showCreateModal,
}) => {
  return (
    <div className={styles.functionContainer}>
      <Row>
        <FunctionList
          page={page}
          dispatch={dispatch}
          showCreateModal={showCreateModal}
          loading={effects['REAPFA0002/query']}
        />
      </Row>
    </div>
  )
}
